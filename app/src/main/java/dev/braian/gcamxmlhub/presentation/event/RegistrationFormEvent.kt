package dev.braian.gcamxmlhub.presentation.event

 sealed class RegistrationFormEvent() {
    data class NameChanged(val name:String): RegistrationFormEvent()
    data class EmailChanged(val email:String): RegistrationFormEvent()
    data class PasswordChanged(val password:String): RegistrationFormEvent()
    data class RepeatedPasswordChanged(val repeatedPassword:String): RegistrationFormEvent()
    data class AcceptTerms(val isAccepted:Boolean): RegistrationFormEvent()
    object Submit: RegistrationFormEvent()
}

data class RegistrationFormState(
   val name:String = "",
   val nameError:String?= "",
   val email: String = "",
   val emailError: String? = "",
   val password: String = "",
   val passwordError: String? = "",
   val repeatedPassword: String = "",
   val repeatedPasswordError: String? = "",
   val acceptedTerms: Boolean = false,
   val isLogin: Boolean = false,
   val isRegistered: Boolean = false,
   val termsError: String? = ""
)