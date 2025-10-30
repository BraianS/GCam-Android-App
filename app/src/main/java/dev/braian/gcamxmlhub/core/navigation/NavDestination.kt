package dev.braian.gcamxmlhub.data.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface NavDestinations {


    @Serializable object Auth : NavDestinations

    @Serializable
    object Login: NavDestinations
    @Serializable
    object SignUp: NavDestinations
    @Serializable
    object ForgotPassword
    

   
        @Serializable
        object MainScreen: NavDestinations
        @Serializable
        object Home: NavDestinations
        @Serializable
        object Search: NavDestinations
        @Serializable
        object Profile: NavDestinations

}