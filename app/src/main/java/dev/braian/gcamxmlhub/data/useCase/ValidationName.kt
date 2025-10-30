package dev.braian.gcamxmlhub.data.useCase

import javax.inject.Inject

class ValidationName @Inject constructor() {

    fun execute(name: String): ValidationResult{

        if(name.isBlank()){
            return ValidationResult(
                successful = false,
                errorMessage = "The name can't be blank"
            )
        }
        if(name.length > 20) {
            return ValidationResult(
                successful = false,
                errorMessage = "The name can't exceed 20 characters"
            )
        }
        return ValidationResult(
            successful = true
        )

    }
}