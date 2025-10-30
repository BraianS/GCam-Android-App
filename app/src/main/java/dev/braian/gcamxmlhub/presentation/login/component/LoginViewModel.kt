package dev.braian.gcamxmlhub.presentation.login.component

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.credentials.GetCredentialResponse
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.braian.gcamxmlhub.core.ApiResult
import dev.braian.gcamxmlhub.data.repository.impl.LoginService
import dev.braian.gcamxmlhub.data.repository.impl.ValidationEvent
import dev.braian.gcamxmlhub.data.useCase.LoginFormState
import dev.braian.gcamxmlhub.data.useCase.ValidationEmail
import dev.braian.gcamxmlhub.data.useCase.ValidationName
import dev.braian.gcamxmlhub.data.useCase.ValidationPassword
import dev.braian.gcamxmlhub.data.useCase.ValidationRepeatPassword
import dev.braian.gcamxmlhub.data.useCase.ValidationTerms
import dev.braian.gcamxmlhub.data.utils.Constant
import dev.braian.gcamxmlhub.presentation.event.LoginFormEvent
import dev.braian.gcamxmlhub.presentation.event.RegistrationFormEvent
import dev.braian.gcamxmlhub.presentation.event.RegistrationFormState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginService: LoginService,
    private val validationName: ValidationName,
    private val validationEmail: ValidationEmail,
    private val validatePassword: ValidationPassword,
    private val validateRepeatPassword: ValidationRepeatPassword,
    private val validationTerms: ValidationTerms
) : ViewModel() {
    val currentUser = loginService.currentUSer

    var state by mutableStateOf(LoginFormState())

    var registrationFormState by mutableStateOf(RegistrationFormState())

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()


    private val _loginState = MutableStateFlow<ApiResult<Boolean>>(ApiResult.Loading)
    val loginState: StateFlow<ApiResult<Boolean>> = _loginState.asStateFlow()


    fun signWithGoogle(context: Context) {
        viewModelScope.launch {
            _loginState.value = ApiResult.Loading

            // 1. Get Google credentials (ID Token)
            val credentialResult = loginService.signWithGoogle(context)

            when (credentialResult) {
                is ApiResult.Success -> {
                    val idToken = credentialResult.data
                    // 2. Sign in to Firebase with the ID Token

                    Log.d(Constant.LogClass.LOGIN_VIEWMODEL, "ID Token: $idToken")
                }

                is ApiResult.Error -> {
                    _loginState.value = ApiResult.Error(credentialResult.exception)
                }

                else -> {}
            }
        }
    }


    fun handleGoogleSignInResult(
        result: GetCredentialResponse
    ) {
        viewModelScope.launch {
            _loginState.value = ApiResult.Loading
            val googleIdToken = (result.credential as? GoogleIdTokenCredential)?.idToken
            if (googleIdToken != null) {
                val authResult = loginService.signInWithGoogleToken(googleIdToken)
                _loginState.value = authResult
            } else {
                _loginState.value = ApiResult.Error(Exception("Failed to get Google ID token."))
            }
        }
    }

    fun handleGoogleSignInError(e: Exception) {
        _loginState.value = ApiResult.Error(e)
    }


    fun onEvent(event: LoginFormEvent) {
        when (event) {
            is LoginFormEvent.EmailChanged -> {
                // 1. Atualiza o estado com o novo email
                state = state.copy(email = event.email)
                // 2. Imediatamente valida o email e atualiza o estado de erro
                val emailResult = validationEmail.execute(state.email)
                state = state.copy(emailError = emailResult.errorMessage)
            }

            is LoginFormEvent.PasswordChanged -> {
                // 1. Atualiza o estado com a nova senha
                state = state.copy(password = event.password)
                // 2. Imediatamente valida a senha e atualiza o estado de erro
                val passwordResult = validatePassword.execute(state.password)
                state = state.copy(passwordError = passwordResult.errorMessage)
            }

            is LoginFormEvent.Submit -> {
                submitData()
            }
        }
    }

    fun onEvent(event: RegistrationFormEvent) {
        when (event) {
            is RegistrationFormEvent.NameChanged -> {
                registrationFormState = registrationFormState.copy(name = event.name)
                val nameResult = validationName.execute(registrationFormState.name)
                registrationFormState =
                    registrationFormState.copy(nameError = nameResult.errorMessage)
            }

            is RegistrationFormEvent.EmailChanged -> {
                registrationFormState = registrationFormState.copy(email = event.email)
                val emailResult = validationEmail.execute(registrationFormState.email)
                registrationFormState =
                    registrationFormState.copy(emailError = emailResult.errorMessage)
            }

            is RegistrationFormEvent.PasswordChanged -> {
                registrationFormState = registrationFormState.copy(password = event.password)
                val passwordResult = validatePassword.execute(registrationFormState.password)
                registrationFormState =
                    registrationFormState.copy(passwordError = passwordResult.errorMessage)
            }

            is RegistrationFormEvent.RepeatedPasswordChanged -> {
                registrationFormState =
                    registrationFormState.copy(repeatedPassword = event.repeatedPassword)
                val repeatedPasswordResult = validateRepeatPassword.execute(
                    registrationFormState.password,
                    registrationFormState.repeatedPassword
                )
                registrationFormState =
                    registrationFormState.copy(repeatedPasswordError = repeatedPasswordResult.errorMessage)
            }

            is RegistrationFormEvent.AcceptTerms -> {
                registrationFormState = registrationFormState.copy(acceptedTerms = event.isAccepted)
                val termsResult = validationTerms.execute(registrationFormState.acceptedTerms)
                registrationFormState =
                    registrationFormState.copy(termsError = termsResult.errorMessage)
            }

            is RegistrationFormEvent.Submit -> {
                submitRegisterData()
            }

        }
    }

    private fun submitRegisterData() {
        val nameResult = validationName.execute(registrationFormState.name)
        val emailResult = validationEmail.execute(registrationFormState.email)
        val passwordResult = validatePassword.execute(registrationFormState.password)
        val repeatedPasswordResult =
            validatePassword.execute(registrationFormState.repeatedPassword)

        val hasError = listOf(
            nameResult,
            emailResult,
            passwordResult,
            repeatedPasswordResult
        ).any { !it.successful }

        if (hasError) {
            registrationFormState = registrationFormState.copy(
                emailError = emailResult.errorMessage,
                passwordError = passwordResult.errorMessage,
                repeatedPasswordError = repeatedPasswordResult.errorMessage
            )
            return
        }
        viewModelScope.launch {
            loginService.signInWithEmailAndPassword(
                registrationFormState.name,
                registrationFormState.email, registrationFormState.password
            )
            validationEventChannel.send(ValidationEvent.Success)
        }


    }


    private fun submitData() {
        val emailResult = validationEmail.execute(state.email)
        val passwordResult = validatePassword.execute(state.password)


        val hasError = listOf(
            emailResult,
            passwordResult,

            ).any { !it.successful }

        if (hasError) {
            state = state.copy(
                emailError = emailResult.errorMessage,
                passwordError = passwordResult.errorMessage

            )
            return
        }
        viewModelScope.launch {
            loginService.login(state.email, state.password)
            validationEventChannel.send(ValidationEvent.Success)
        }

    }


    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = ApiResult.Loading
            Log.i(Constant.LogClass.LOGIN_VIEWMODEL, "Login with $email and $password")
            _loginState.value = loginService.login(email, password)
        }
    }


    fun signIn(name: String, email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = ApiResult.Loading
            _loginState.value = loginService.signInWithEmailAndPassword(name, email, password)
        }
    }

    fun logout() {
        viewModelScope.launch {
            _loginState.value = ApiResult.Loading
            try {
                _loginState.value = loginService.logout()
            } catch (e:Exception) {
                Log.e(Constant.LogClass.LOGIN_VIEWMODEL, "Logout error: ${e.message}")
                _loginState.value = ApiResult.Error(e)
            }
        }

    }

    fun handleGoogleSignIn(context: Context) {
        viewModelScope.launch {
            _loginState.value = ApiResult.Loading

            try {
                // Get the response that contains the PendingIntent from the service
                val credentialResponse = loginService.getGoogleCredentialResponse(context)

                // Extract the ID token and sign in to Firebase
                val googleIdToken =
                    (credentialResponse.credential as? GoogleIdTokenCredential)?.idToken
                if (googleIdToken != null) {
                    val authResult = loginService.signInWithGoogleToken(googleIdToken)
                    _loginState.value = authResult
                } else {
                    _loginState.value = ApiResult.Error(Exception("Failed to get Google ID token."))
                }
            } catch (e: Exception) {
                // Handle exceptions here, like NoCredentialException
                _loginState.value = ApiResult.Error(e)
            }

        }
    }
}