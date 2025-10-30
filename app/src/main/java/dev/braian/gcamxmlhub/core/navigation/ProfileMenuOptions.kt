package dev.braian.gcamxmlhub.core.navigation

sealed class ProfileMenuOptions {
    object Subscribe : ProfileMenuOptions()
    object Settings : ProfileMenuOptions()
    object Logout : ProfileMenuOptions()
}