package dev.braian.gcamxmlhub.data.screen


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.braian.gcamxmlhub.core.navigation.GCamNames
import dev.braian.gcamxmlhub.data.model.ContentType
import dev.braian.gcamxmlhub.data.model.FeedContent
import dev.braian.gcamxmlhub.data.utils.Constant
import dev.braian.gcamxmlhub.presentation.components.Feeds
import dev.braian.gcamxmlhub.presentation.components.GCams
import dev.braian.gcamxmlhub.ui.theme.GcamXMLHubTheme


fun getFeedContentForGCam(gcam: GCamNames): List<FeedContent> {
    return listOf(
        FeedContent(
            id = "1",
            title = "Review: ${gcam.name}",
            description = "Análise completa da versão ${gcam.version} desenvolvida por ${gcam.developer}",
            user = dev.braian.gcamxmlhub.data.model.User(
                id = "1",
                name = "Expert Review",
                role = dev.braian.gcamxmlhub.data.model.Role.STANDARD,
                subscription = dev.braian.gcamxmlhub.data.model.Subscription.Basic,


                ),
            date = "15/03/2024",
            likes = 142,

            type = ContentType.REVIEW
        ),
        FeedContent(
            id = "2",
            title = "Dicas de Uso: ${gcam.name}",
            description = "Aprenda a usar todas as funcionalidades desta configuração",
            user = dev.braian.gcamxmlhub.data.model.User(
                id = "1",
                name = "Expert Review",
                role = dev.braian.gcamxmlhub.data.model.Role.STANDARD,
                subscription = dev.braian.gcamxmlhub.data.model.Subscription.Basic,


                ),
            date = "10/03/2024",
            likes = 89,

            type = ContentType.TIPS
        ),
        FeedContent(
            id = "3",
            title = "Comparativo: ${gcam.name} vs Outras GCams",
            description = "Veja como esta GCam se compara com outras do mercado",
            user = dev.braian.gcamxmlhub.data.model.User(
                id = "1",
                name = "Expert Review",
                role = dev.braian.gcamxmlhub.data.model.Role.STANDARD,
                subscription = dev.braian.gcamxmlhub.data.model.Subscription.Basic,


                ),
            date = "05/03/2024",
            likes = 204,

            type = ContentType.COMPARISON
        ),
        FeedContent(
            id = "4",
            title = "Problemas Comuns - ${gcam.name}",
            description = "Soluções para os problemas mais frequentes nesta versão",
            user = dev.braian.gcamxmlhub.data.model.User(
                id = "1",
                name = "Expert Review",
                role = dev.braian.gcamxmlhub.data.model.Role.STANDARD,
                subscription = dev.braian.gcamxmlhub.data.model.Subscription.Basic,


                ),
            date = "01/03/2024",
            likes = 67,

            type = ContentType.TUTORIAL
        ),
        FeedContent(
            id = "5",
            title = "Atualização: ${gcam.name} ${gcam.version}",
            description = "Novidades e melhorias na versão mais recente",
            user = dev.braian.gcamxmlhub.data.model.User(
                id = "1",
                name = "Expert Review",
                role = dev.braian.gcamxmlhub.data.model.Role.STANDARD,
                subscription = dev.braian.gcamxmlhub.data.model.Subscription.Basic,


                ),

            date = "25/02/2024",
            likes = 156,
            
            type = ContentType.UPDATE
        )
    )
}

// Composables principais
@Composable
fun SearchScreen() {
    var selectedGCam by remember { mutableStateOf<GCamNames?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(16.dp)
        ) {

            
            if(selectedGCam == null ){
                Text(
                    text = "Selecione uma GCam:",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 16.dp)
                )


                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                ) {
                    items(Constant.gCamSettings.getAllGcamNames) { gcam ->
                        GCams(
                            gcam = gcam,
                            isSelected = selectedGCam == gcam,
                            onItemClick = {
                                selectedGCam = gcam
                            }
                        )
                    }
                }
            }
            
            else {
                Column {
                    // Botão para voltar
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                            .clickable { selectedGCam = null },
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {

                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Voltar",
                            modifier = Modifier.padding(16.dp),
                            tint = MaterialTheme.colorScheme.tertiary
                        )
                        Text(
                            text = "Voltar para a lista",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(16.dp),
                            color = Color.White
                        )
                    }

                    Feeds(gcam = selectedGCam!!)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

        }
    }
}


@Preview(showBackground = true, widthDp = 360, heightDp = 600)
@Composable
fun SearchScreenPreview() {
    GcamXMLHubTheme {
        SearchScreen()
    }
}

