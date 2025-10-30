package dev.braian.gcamxmlhub.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.braian.gcamxmlhub.R

data class FeedItem(
    val id: Int,
    val title: String,
    val description: String,
    val imageUrl: String,
    val linkUrl: String,
    val createdBy: String,
    val creationDate: String,
    val isLiked: Boolean = false
)

val mockFeedItems = listOf(
    // URLs de exemplo. Em um app real, seriam URLs do Firebase Storage ou CDN.
    FeedItem(1, "Golden Gate Bridge", "A beautiful view of the famous bridge in San Francisco.", "https://example.com/golden_gate.jpg", "https://gcam.com/xml/golden_gate.xml", "user_123", "2023-01-15"),
    FeedItem(2, "Sunset in Rio", "The sun setting over the iconic Sugarloaf Mountain.", "https://example.com/sunset_rio.jpg", "https://youtube.com/watch?v=123", "user_456", "2023-01-16"),
    FeedItem(3, "Kyoto Bamboo Forest", "Walking through the serene bamboo forest in Japan.", "https://example.com/kyoto_bamboo.jpg", "https://noticias.com/kyoto-bamboo-forest", "user_789", "2023-01-17"),
    FeedItem(4, "New York City Night", "The city that never sleeps, with a stunning night skyline.", "https://example.com/nyc_night.jpg", "https://gcam.com/xml/nyc.xml", "user_123", "2023-01-18"),
    FeedItem(5, "Paris Eiffel Tower", "A classic shot of the Eiffel Tower at dusk.", "https://example.com/eiffel_tower.jpg", "https://youtube.com/watch?v=456", "user_456", "2023-01-19")
)

@Composable
fun FeedItem(item: FeedItem) {
    var isLiked by remember { mutableStateOf(item.isLiked) }

    // TODO: Adicione uma função para abrir a URL no navegador ou no app
    // Exemplo:
    // fun openUrl(context: Context, url: String) {
    //     val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    //     context.startActivity(intent)
    // }

    Card (
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            ,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column (
            modifier = Modifier.padding(16.dp)
        ) {
            // Imagem (usando um placeholder para o exemplo)
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground), // AQUI você usaria uma função de carregamento de URL, ex: rememberImagePainter(data = item.imageUrl)
                contentDescription = item.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Título e descrição
            Text(
                text = item.title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = item.description,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Informações do criador e data
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Por: ${item.createdBy}",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                )
                Text(
                    text = "Criado em: ${item.creationDate}",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botão de "Curtir"
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton (
                    onClick = {
                        isLiked = !isLiked
                    }
                ) {
                    Icon(
                        imageVector = if (isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Like button",
                        tint = if (isLiked) Color.Red else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Text(
                    text = if (isLiked) "Curtido!" else "Curtir",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
@Preview
@Composable
fun FeedItemPreview() {
    // É uma boa prática envolver o preview em MaterialTheme
    MaterialTheme {
        // Dados de exemplo para o preview.
        val feedItem: FeedItem = FeedItem(
            id = 1,
            title = "Exemplo de Título",
            description = "Uma breve descrição do conteúdo do feed.",
            imageUrl = "https://example.com/image.jpg",
            linkUrl = "https://gcam.com/xml/preview.xml",
            createdBy = "preview_user",
            creationDate = "2023-08-27"
        )
        FeedItem(feedItem)
    }
}
