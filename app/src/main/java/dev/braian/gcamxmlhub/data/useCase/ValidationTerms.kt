package dev.braian.gcamxmlhub.data.useCase

import javax.inject.Inject

class ValidationTerms @Inject constructor (){

    fun execute(acceptedTerms:Boolean): ValidationResult {

        if(!acceptedTerms) {
            return ValidationResult(
                successful = false,
                errorMessage = "Please accept the terms"
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}
