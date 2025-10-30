package dev.braian.gcamxmlhub.data.useCase

import javax.inject.Inject

class ValidationPassword @Inject constructor(){

    fun execute(password:String): ValidationResult {

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
        return ValidationResult(
            successful = true
        )
    }
}
