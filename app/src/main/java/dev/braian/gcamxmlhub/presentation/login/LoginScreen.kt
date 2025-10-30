package dev.braian.gcamxmlhub.data.screen

import android.annotation.SuppressLint
import android.content.Context
import android.credentials.GetCredentialException
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.credentials.CredentialManager
import androidx.credentials.exceptions.NoCredentialException
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.Firebase
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import dev.braian.gcamxmlhub.R
import dev.braian.gcamxmlhub.core.ApiResult
import dev.braian.gcamxmlhub.data.utils.Constant
import dev.braian.gcamxmlhub.presentation.components.CircularLoading
import dev.braian.gcamxmlhub.presentation.components.TopHeader
import dev.braian.gcamxmlhub.presentation.event.LoginFormEvent
import dev.braian.gcamxmlhub.presentation.login.component.LoginViewModel
import dev.braian.gcamxmlhub.ui.theme.AccentColor
import kotlinx.coroutines.launch
import java.security.MessageDigest
import java.util.UUID

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginScreen(
    context:Context,

    onSignInSuccess: () -> Unit,
    onNavigateToSignUp: () -> Unit,
    onNavigateToForgotPassword: () -> Unit

    ) {

    val loginViewModel: LoginViewModel = hiltViewModel()

    val state = loginViewModel.state

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    when(state){
        is ApiResult.Loading -> CircularLoading()
        is ApiResult.Success<*> -> {
            if((state as ApiResult.Success<Boolean>).data){
                Toast.makeText(context, "Login Success!", Toast.LENGTH_SHORT).show()
            }
        }
        is ApiResult.Error -> {
            val errorMessage = (state as ApiResult.Error).exception.message
            Toast.makeText(context, "Login failed: $errorMessage", Toast.LENGTH_SHORT).show()
        }
    }

    fun Nonce(): String {
        val rawNonce = UUID.randomUUID().toString()
        val bytes = rawNonce.toByteArray(Charsets.UTF_8)
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.fold("") { str, it -> str + "%02x".format(it) }
    }



    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    suspend fun signWithGoogle(context: Context) {

        val credentialManager = CredentialManager.create(context)

        val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(true)
            .setServerClientId("608299041384-ssk0td9fhpmljkkekutu3iadkehkkh82.apps.googleusercontent.com")
            .setAutoSelectEnabled(true)
            // nonce string to use when generating a Google ID token
            .setNonce(Nonce())

            .build()



        Log.d(Constant.LogClass.LOGIN_SCREEN, "Google Options " +googleIdOption.toString())


        val request = androidx.credentials.GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        Log.d(Constant.LogClass.LOGIN_SCREEN, "Google Request " +request.toString())


        try {
            // 3. Call the correct suspend function (no context parameter)
            val result = credentialManager.getCredential(context, request)
            val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(result.credential.data)

            // 4. Use the token to sign in with Firebase
            val idToken = googleIdTokenCredential.idToken
            val credential = GoogleAuthProvider.getCredential(idToken, null)

            Firebase.auth.signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(context, "Sign-in Success!", Toast.LENGTH_SHORT).show()
                        onSignInSuccess() // Navigate on success
                    } else {
                        Log.w("LoginScreen", "Firebase sign-in failed", task.exception)
                        Toast.makeText(context, "Authentication Failed.", Toast.LENGTH_SHORT).show()
                    }
                }

        } catch (e: GetCredentialException) {
            // 5. Handle specific exceptions from CredentialManager for better UX
            when (e) {

                is NoCredentialException -> {
                    Log.d("LoginScreen", "No credential found.", e)
                    Toast.makeText(context, "No accounts found. Please sign in to Google on this device.", Toast.LENGTH_LONG).show()
                }
                else -> {
                    Log.e("LoginScreen", "GetCredentialException: ", e)
                    Toast.makeText(context, "An unknown error occurred.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }



    Box(
        modifier = Modifier
            .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
    ) {

        TopHeader(title = "Gcam Global")

        // Conteúdo central
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(100.dp)) // Espaço para não sobrepor a onda superior

            Text(
                text = "Get start free",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = state.email,
                onValueChange = {
                    loginViewModel.onEvent(LoginFormEvent.EmailChanged(it))
                },
                isError = state.emailError != null,
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(25.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            if(state.emailError != null) {
                state.emailError?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.End)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = state.password,
                onValueChange = {
                    loginViewModel.onEvent(LoginFormEvent.PasswordChanged(it))
                },
                isError = loginViewModel.state.passwordError != null,
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(25.dp),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            if(loginViewModel.state.passwordError != null) {
                Text(
                    text = loginViewModel.state.passwordError!!,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.End)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            TextButton(
                onClick = onNavigateToForgotPassword,
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(
                    text = "Forgot your password?",
                    color = MaterialTheme.colorScheme.tertiary,
                    fontSize = 12.sp
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    loginViewModel.onEvent(LoginFormEvent.Submit)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
            ) {
                Text(
                    text = "Login",
                    color = AccentColor,
                    fontSize = 18.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Or sign with",
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            //Google Login
            Button(
                onClick = {
                    scope.launch {
                      signWithGoogle(context)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.icons8_google),
                        contentDescription = "Google Icon",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Google", color = MaterialTheme.colorScheme.onSurface)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Don't have an account?",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                TextButton(
                    onClick = onNavigateToSignUp
                ) {
                    Text(
                        text = "Sign Up",
                        color = MaterialTheme.colorScheme.tertiary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}





