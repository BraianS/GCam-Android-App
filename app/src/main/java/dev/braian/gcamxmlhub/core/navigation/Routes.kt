package dev.braian.gcamxmlhub.data.navigation

sealed class Routes(val route: String) {
    object Splash : Routes("splash_screen")
    sealed class Auth(route: String) : Routes(route) {
        object Login : Auth("login_screen")
        object SignUp : Auth("signup_screen")
        object ForgotPassword : Auth("forgot_password_screen")
    }
    sealed class Main(route: String) : Routes(route) {
        object MainScreen : Main("main_screen")
        object Home : Main("home_screen")
        object Search : Main("search_screen")
        object Profile : Main("profile_screen")
        object Settings : Main("settings_screen")
        object Notifications : Main("notifications_screen")
        object Subscription : Main("subscription_screen")
    }
}
