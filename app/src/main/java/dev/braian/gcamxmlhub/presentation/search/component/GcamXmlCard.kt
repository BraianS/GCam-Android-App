package dev.braian.gcamxmlhub.presentation.search.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.braian.gcamxmlhub.data.model.ContentType
import dev.braian.gcamxmlhub.data.model.GcamXml
import dev.braian.gcamxmlhub.ui.theme.GcamXMLHubTheme

@Composable
fun GCamXmlCard(xml: GcamXml) {
    // Estado interno para a funcionalidade de "Curtir" (simulação)
    var isLiked by remember { mutableStateOf(false) }

    Card (
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { /* TODO: Ação ao clicar no card, como abrir detalhes */ },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary,
        ),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column (modifier = Modifier.padding(16.dp)) {
            // Título e Ícone de Verificação (se o XML for moderado)
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = xml.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Autor e Versão da GCam
            // Autor (Destaque) e Versão da GCam (usando AnnotatedString para cores diferentes)
            Text(
                text = buildAnnotatedString {
                    // Texto "Por "
                    append("Por ")

                    // Nome do Autor (Destaque Terciário)
                    withStyle(style = SpanStyle(
                        color = MaterialTheme.colorScheme.tertiary, // Cor desejada
                        fontWeight = FontWeight.SemiBold
                    )) {
                        append(xml.user.name)
                    }

                    // Separador e Versão da GCam
                    withStyle(style = SpanStyle(
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                    )) {
                        append(" | Versão GCam: ${xml.requiredGCamVersion}")
                    }
                },
                fontSize = 12.sp,
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Descrição (Compatibilidade)
            Text(
                text = "Compatível: ${xml.compatibleDevices}",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1 // Garante que a compatibilidade não tome muito espaço
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Metadados (Likes e Downloads)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                MetadataItem(Icons.Default.Favorite, "${xml.likes} Likes")
                MetadataItem(Icons.Default.Done, "${xml.downloadCount} Downloads")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Ações (Curtir e Baixar)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Botão Curtir
                OutlinedButton(
                    onClick = { isLiked = !isLiked },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = if (isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Curtir",
                        tint = if (isLiked) Color.Red else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = if (isLiked) "Curtido!" else "Curtir")
                }

                // Botão Baixar
                Button (
                    onClick = { /* TODO: Lógica de download */ },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                ) {
                    Icon(
                        imageVector = Icons.Default.Download,
                        contentDescription = "Baixar XML",
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Baixar", color = MaterialTheme.colorScheme.tertiary)
                }
            }
        }
    }
}

@Composable
fun MetadataItem(icon: ImageVector, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(16.dp),
            tint = MaterialTheme.colorScheme.primary // Usa a cor primária para destaque
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
        )
    }
}


@Preview(showBackground = true, name = "GCam XML Card Default")
@Composable
fun GcamXmlCardPreview() {
    // Dados de exemplo para o Card
    val mockXml = GcamXml(
        id = "1",
        title = "Ultra HDR V7",
        description = "Configuração perfeita para fotos com alta faixa dinâmica e cores vibrantes. Ideal para ambientes externos.",
        user = dev.braian.gcamxmlhub.data.model.User(
            id = "1",
            name = "Expert Review",
            role = dev.braian.gcamxmlhub.data.model.Role.STANDARD,
            subscription = dev.braian.gcamxmlhub.data.model.Subscription.Basic,


            ),
        date = "2025-10-01",
        likes = 952,
        type = ContentType.XML,
        imageUrl = "",
        requiredGCamVersion = "BSG 8.9",
        compatibleDevices = "OnePlus 11/12, Poco F5",
        downloadCount = 4120,
    )

    GcamXMLHubTheme {
        GCamXmlCard(xml = mockXml)
    }
}