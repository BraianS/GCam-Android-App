package dev.braian.gcamxmlhub.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp

import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import dev.braian.gcamxmlhub.R
import dev.braian.gcamxmlhub.data.model.ContentType
import dev.braian.gcamxmlhub.data.model.FeedContent
import dev.braian.gcamxmlhub.ui.theme.GcamXMLHubTheme

@Composable
fun FeedCard(content: FeedContent) {

    val typeColor = when (content.type) {
        ContentType.REVIEW -> Color(0xFFFFA000)
        ContentType.TUTORIAL -> Color(0xFF1976D2)
        ContentType.COMPARISON -> Color(0xFF7B1FA2)
        ContentType.NEWS -> Color(0xFF388E3C)
        ContentType.UPDATE -> Color(0xFFF57C00)
        ContentType.TIPS -> Color(0xFF0288D1)
        ContentType.XML -> Color(0xFFF50057)
    }

    var imageLoaded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .height(140.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            // Imagem lateral
            if (content.imageUrl.isNotBlank()) {
                AsyncImage(
                    model = content.imageUrl,
                    contentDescription = "Imagem: ${content.title}",
                    modifier = Modifier
                        .width(120.dp)
                        .fillMaxHeight(),
                    contentScale = ContentScale.Crop,
                    onSuccess = { imageLoaded = true },
                    placeholder = painterResource(id = R.drawable.place_holder),
                    error = painterResource(id = R.drawable.place_holder_error)
                )
            }

            // Conteúdo textual
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    // Badge do tipo
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(bottom = 8.dp)
                    ) {
                        Icon(
                            imageVector = content.type.icon,
                            contentDescription = content.type.name,
                            tint = typeColor,
                            modifier = Modifier.size(12.dp)
                        )
                        Text(
                            text = content.type.name,
                            style = MaterialTheme.typography.labelSmall,
                            color = typeColor,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }

                    // Título
                    Text(
                        text = content.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                // Footer
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "por ${content.author}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Row {
                        Icon(
                            imageVector = Icons.Default.ThumbUp,
                            contentDescription = "Likes",
                            modifier = Modifier.size(12.dp)
                        )
                        Text(
                            text = "${content.likes}",
                            style = MaterialTheme.typography.labelSmall,
                            modifier = Modifier.padding(start = 2.dp)
                        )
                    }
                }
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically)
                {
                    Text(
                        text = "${content.date}",
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.padding(start = 2.dp)
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun FeedCardPreview() {
    GcamXMLHubTheme {
        FeedCard(
            content = FeedContent(
                id = "1",
                title = "Review Completo: LMC Camera",
                description = "Análise detalhada da versão R15 desenvolvida por Hasli",
                author = "TechReview Team",
                date = "15/03/2024",
                imageUrl = "https://picsum.photos/400/200?random=1&grayscale",
                likes = 156,
                type = ContentType.REVIEW
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AllContentTypesPreviewAlternative() {
    GcamXMLHubTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            FeedCard(
                content = FeedContent(
                    id = "1",
                    title = "Review Completo",
                    description = "Análise detalhada da última versão",
                    author = "Expert Review",
                    date = "15/03/2024",
                    likes = 156,
                    imageUrl = "https://picsum.photos/400/200?random=1&grayscale",
                    type = ContentType.REVIEW
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            FeedCard(
                content = FeedContent(
                    id = "2",
                    title = "Tutorial Completo",
                    description = "Aprenda todas as configurações",
                    author = "Tutorial Master",
                    date = "12/03/2024",
                    likes = 89,
                    imageUrl = "https://picsum.photos/400/200?random=2",
                    type = ContentType.TUTORIAL
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            FeedCard(
                content = FeedContent(
                    id = "3",
                    title = "Análise Comparativa",
                    description = "Compare as melhores GCams",
                    author = "Analysis Pro",
                    date = "08/03/2024",
                    likes = 204,
                    imageUrl = "https://picsum.photos/400/200?random=3",
                    type = ContentType.COMPARISON
                )
            )
        }
    }
}