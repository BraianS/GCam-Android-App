package dev.braian.gcamxmlhub.data.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import dev.braian.gcamxmlhub.data.screen.ForgotPasswordScreen
import dev.braian.gcamxmlhub.data.screen.LoginScreen
import dev.braian.gcamxmlhub.data.screen.MainScreen
import dev.braian.gcamxmlhub.data.screen.SignUpScreen
import dev.braian.gcamxmlhub.presentation.login.component.LoginViewModel


@Composable
fun Navigation() {
    val loginViewModel: LoginViewModel = hiltViewModel()
    val currentUser by loginViewModel.currentUser.collectAsStateWithLifecycle()

    val navController = rememberNavController()

    var startDestination = remember { mutableStateOf<NavDestinations>(NavDestinations.Auth) }

    LaunchedEffect(currentUser) {
        if (currentUser == null) {
            startDestination.value = NavDestinations.Auth
        } else {
            startDestination.value = NavDestinations.MainScreen
        }
    }

    var context = LocalContext.current
    LoginScreen(
        context = context,
        onSignInSuccess = {
            navController.navigate(NavDestinations.MainScreen) {
                popUpTo(navController.graph.id) { inclusive = true }
            }
        },
        onNavigateToSignUp = {
            navController.navigate(NavDestinations.SignUp)
        },
        onNavigateToForgotPassword = {
            navController.navigate(NavDestinations.ForgotPassword)
        }
    )

    // Apenas renderiza o NavHost depois que o destino inicial for determinado.
    if (startDestination != null) {
        NavHost(navController = navController, startDestination = if(currentUser != null) NavDestinations.MainScreen else NavDestinations.Auth) {

            // Gráfico de navegação para as telas de autenticação
            navigation<NavDestinations.Auth>(startDestination = NavDestinations.Login) {
                composable<NavDestinations.Login> {
                    LoginScreen(
                       context = context,
                        onSignInSuccess = {
                            navController.navigate(NavDestinations.MainScreen) {
                                popUpTo(navController.graph.id) { inclusive = true }
                            }
                        },
                        onNavigateToSignUp = {
                            navController.navigate(NavDestinations.SignUp)
                        },
                        onNavigateToForgotPassword = {
                            navController.navigate(NavDestinations.ForgotPassword)
                        }
                    )
                }
                composable<NavDestinations.SignUp> {
                    SignUpScreen(
                        loginViewModel
                    )
                }
                composable<NavDestinations.ForgotPassword> {
                    ForgotPasswordScreen(
                        onNavigateToLogin = { navController.navigate(NavDestinations.Login) }
                    )
                }
            }

            // Gráfico de navegação principal da aplicação
            composable<NavDestinations.MainScreen> {
                MainScreen()
            }
        }
    }
}