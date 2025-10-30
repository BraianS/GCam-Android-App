package dev.braian.gcamxmlhub.data.model

import kotlinx.serialization.Serializable

@Serializable
sealed class Subscription(val uploadLimit: Int) {
    @Serializable
    object Basic : Subscription(uploadLimit = 1)       // Limite de 1 XML
    @Serializable
    object Uploader : Subscription(uploadLimit = 3)    // Limite de 3 XMLs
    @Serializable
    object Premium : Subscription(uploadLimit = 10)   // Limite de 10 XMLs
}