package dev.braian.gcamxmlhub.data.useCase

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String?= null
)