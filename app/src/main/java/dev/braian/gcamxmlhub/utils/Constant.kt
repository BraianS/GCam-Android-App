package dev.braian.gcamxmlhub.data.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import dev.braian.gcamxmlhub.core.navigation.GCamNames
import dev.braian.gcamxmlhub.data.navigation.TopLevelRoute
import dev.braian.gcamxmlhub.presentation.components.FeedItem


class Constant {



    object  gCamSettings {

        val getAllGcamNames =  listOf(
            GCamNames.LMC,
            GCamNames.BSG,
            GCamNames.Greatness,
            GCamNames.Shamim,
            GCamNames.Arnova8G2,
            GCamNames.Parrot043,
            GCamNames.Wichaya,
            GCamNames.BigKaka,
            GCamNames.Hasli,
            GCamNames.Urnyx05,
            GCamNames.Nikita,
            GCamNames.TrCamera,
            GCamNames.AGC,
            GCamNames.NGCam,
            GCamNames.Firebird,
            GCamNames.MTSL,
            GCamNames.PX,
            GCamNames.San1ty,
            GCamNames.Fu24,
            GCamNames.Javas
        )

    }


    object  Routes {
        val BottomBarRoutes = listOf(
            TopLevelRoute("Profile", "Profile", Icons.Default.Person, MainContent.Home.route),
            TopLevelRoute("Profile", "Profile", Icons.Default.Person, MainContent.Search.route),
            TopLevelRoute("Profile", "Profile", Icons.Default.Person, MainContent.Profile.route),
        )
    }

    object AppStyles {
        val titleStyle = TextStyle(
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )


    }

    object Mock {
        val mockFeedItems = listOf<FeedItem>(
            // URLs de exemplo. Em um app real, seriam URLs do Firebase Storage ou CDN.
            FeedItem(1, "Golden Gate Bridge", "A beautiful view of the famous bridge in San Francisco.", "https://example.com/golden_gate.jpg", "https://gcam.com/xml/golden_gate.xml", "user_123", "2023-01-15"),
            FeedItem(2, "Sunset in Rio", "The sun setting over the iconic Sugarloaf Mountain.", "https://example.com/sunset_rio.jpg", "https://youtube.com/watch?v=123", "user_456", "2023-01-16"),
            FeedItem(3, "Kyoto Bamboo Forest", "Walking through the serene bamboo forest in Japan.", "https://example.com/kyoto_bamboo.jpg", "https://noticias.com/kyoto-bamboo-forest", "user_789", "2023-01-17"),
            FeedItem(4, "New York City Night", "The city that never sleeps, with a stunning night skyline.", "https://example.com/nyc_night.jpg", "https://gcam.com/xml/nyc.xml", "user_123", "2023-01-18"),
            FeedItem(5, "Paris Eiffel Tower", "A classic shot of the Eiffel Tower at dusk.", "https://example.com/eiffel_tower.jpg", "https://youtube.com/watch?v=456", "user_456", "2023-01-19")
        )
    }

    object LogClass {
        val MAIN_SCREEN = "MainScreen"
        val HOME_SCREEN = "HomeScreen"
        val PROFILE_SCREEN = "ProfileScreen"
        val SEARCH_SCREEN = "SearchScreen"
        val LOGIN_SCREEN = "LoginScreen"
        val NAVIGATION = "Navigation"
        val LOGIN_SERVICE = "LoginService"
        val LOGIN_VIEWMODEL = "LoginViewModel"
        const val GOOGLE_SIGN_IN_REQUEST = "googleSignInRequest"
        const val GOOGLE_SIGN_UP_REQUEST = "googleSignUpRequest"
    }


}

