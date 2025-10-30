package dev.braian.gcamxmlhub.data.useCase

import javax.inject.Inject

class ValidationRepeatPassword @Inject constructor(){
    fun execute(password:String, repeatedPassword: String): ValidationResult {

        val containsLettersAndDigits = password.any { it.isDigit() } &&
                    password.any { it.isLetter() }

        if(password.length < 8){
            return ValidationResult(
                successful = false,
                errorMessage = "The password needs to consist of at least 8 characters"
            )
        }
        if(!containsLettersAndDigits){
            return ValidationResult(
                successful = false,
                errorMessage = "The password needs to contain at least one letter and digit"
            )
        }

        if(password != repeatedPassword){
            return ValidationResult(
                successful = false,
                errorMessage = "The passwords don't match"
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}
