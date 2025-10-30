package dev.braian.gcamxmlhub.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("role") val role: Role,
    @SerialName("subscription") val subscription: Subscription
)