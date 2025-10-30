package dev.braian.gcamxmlhub.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SubscriptionPlan(
    @SerialName("months") val months: Int,
    @SerialName("price") val price : Double,
    @SerialName("info") val info: String
)
