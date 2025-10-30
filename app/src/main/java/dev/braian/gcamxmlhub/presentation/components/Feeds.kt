package dev.braian.gcamxmlhub.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.braian.gcamxmlhub.core.navigation.GCamNames
import dev.braian.gcamxmlhub.data.screen.getFeedContentForGCam

@Composable
fun Feeds(gcam: GCamNames) {
    val feedContent = remember(gcam) { getFeedContentForGCam(gcam) }

    Column {
        Text(
            text = "Conteúdo sobre ${gcam.name}:",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
        ) {
            items(feedContent) { content ->
                FeedCard(content = content)
            }
        }
    }
}