package dev.braian.gcamxmlhub.data.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.google.firebase.auth.FirebaseUser
import dev.braian.gcamxmlhub.R
import dev.braian.gcamxmlhub.data.navigation.Routes
import dev.braian.gcamxmlhub.presentation.components.MenuButton
import dev.braian.gcamxmlhub.presentation.login.component.LoginViewModel
import dev.braian.gcamxmlhub.ui.theme.GcamXMLHubTheme

@Composable
fun ProfileScreen(
    userAuth: FirebaseUser?,
    loginViewModel: LoginViewModel = hiltViewModel<LoginViewModel>()
) {
   val navController = rememberNavController()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Top Card for Profile Image and Name
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .align(Alignment.CenterHorizontally)
                .clip(MaterialTheme.shapes.medium)
                .background(MaterialTheme.colorScheme.secondary)
        )  {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Profile Picture or Placeholder
                if (userAuth?.photoUrl != null) {
                    AsyncImage(
                        model = userAuth.photoUrl,
                        contentDescription = "Profile Picture",
                        modifier = Modifier
                            .size(150.dp)
                            .clip(CircleShape)
                    )
                } else {
                    // Placeholder image if no photo URL is available
                    // You would replace this with your actual drawable resource, e.g., R.drawable.ic_profile_placeholder
                    Image(
                        painter = painterResource(id = R.drawable.baseline_person_pin_24 ),
                        contentDescription = "Profile Picture Placeholder",
                        modifier = Modifier
                            .size(150.dp)
                            .clip(CircleShape)
                            ,
                        colorFilter = ColorFilter.tint(Color.White)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = userAuth?.displayName ?: "Guest",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        // Menu Options
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            MenuButton(
                text = "Subscribe",
                icon = Icons.Default.Star,
                onClick = { navController.navigate(Routes.Main.Subscription)}
            )
            MenuButton(
                text = "Settings",
                icon = Icons.Default.Settings,
                onClick = { navController.navigate(Routes.Main.Settings)}
            )
            MenuButton(
                text = "Logout",
                icon = Icons.Default.Close,
                isLogout = true,
                onClick = { loginViewModel.logout() }
            )
        }
    }
}

@Preview
@Composable
fun ProfileScreenLightPreview() {
    GcamXMLHubTheme(darkTheme = false) {
        ProfileScreen(
            userAuth = null
        )
    }

}
@Preview
@Composable
fun ProfileScreenDarkPreview() {
    GcamXMLHubTheme(darkTheme = true) {
        ProfileScreen(
            userAuth = null
        )
    }

}