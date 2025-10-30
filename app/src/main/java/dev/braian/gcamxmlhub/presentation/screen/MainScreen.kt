package dev.braian.gcamxmlhub.data.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import dev.braian.gcamxmlhub.data.navigation.NavDestinations
import dev.braian.gcamxmlhub.presentation.login.component.LoginViewModel


@Composable
fun MainScreen() {
    // Um NavController interno para a navegação aninhada da barra inferior
    val bottomNavController = rememberNavController()
    val navBackStackEntry by bottomNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val firebaseUser = Firebase.auth.currentUser

    val bottomBarRoutes = listOf(
        NavDestinations.Home,
        NavDestinations.Search,
        NavDestinations.Profile
    )

    val loginViewModel: LoginViewModel = hiltViewModel()

    Scaffold(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.primary),
        bottomBar = {
            NavigationBar {
                bottomBarRoutes.forEach { screen ->
                    // Certifique-se de que a rota atual e a rota da tela são do mesmo tipo
                    val isSelected = currentRoute == screen::class.java.canonicalName

                    NavigationBarItem(
                        selected = isSelected,
                        onClick = {
                            if (!isSelected) {
                                bottomNavController.navigate(screen) {
                                    // Evita múltiplos itens na pilha de navegação
                                    popUpTo(bottomNavController.graph.findStartDestination().id) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        },
                        icon = {
                            Icon(
                                when (screen) {
                                    NavDestinations.Home -> Icons.Filled.Home
                                    NavDestinations.Search -> Icons.Filled.Search
                                    NavDestinations.Profile -> Icons.Filled.Person
                                    else -> Icons.Filled.Home // Default
                                },
                                contentDescription = screen::class.java.simpleName
                            )
                        },
                        label = { Text(screen::class.java.simpleName) }
                    )
                }
            }
        }
    ) { innerPadding ->

        NavHost(
            navController = bottomNavController,
            startDestination = NavDestinations.Home,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<NavDestinations.Home> {
                HomeScreen()
            }
            composable<NavDestinations.Search> {
                SearchScreen()
            }
            composable<NavDestinations.Profile> {
                ProfileScreen(
                    userAuth = firebaseUser
                )
            }
        }
    }
}