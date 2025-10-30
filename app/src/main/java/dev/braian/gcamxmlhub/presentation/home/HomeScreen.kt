package dev.braian.gcamxmlhub.data.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.braian.gcamxmlhub.presentation.components.CardWelcome
import dev.braian.gcamxmlhub.presentation.login.component.LoginViewModel

@Composable
@Preview
fun HomeScreen( ) {
    val viewModel: LoginViewModel = hiltViewModel()
    val currentUser = viewModel.currentUser

    val userName = viewModel.currentUser.value?.displayName ?: ""

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(16.dp)
        ) {
            CardWelcome(userName)

            Spacer(Modifier.height(16.dp))

            //FeedCard()
        }
    }
}

