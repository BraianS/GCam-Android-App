package dev.braian.gcamxmlhub.data.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.ui.graphics.vector.ImageVector

enum class ContentType( name: String, val icon: ImageVector) {
    REVIEW("Review", Icons.Default.Star),
    TUTORIAL("Tutorial", Icons.Default.Notifications),
    COMPARISON("Comparação", Icons.Default.Check),
    NEWS("Notícias", Icons.Default.Info),
    UPDATE("Atualização", Icons.Default.ThumbUp),
    TIPS("Dicas", Icons.Default.MailOutline),
    XML("XML", Icons.Default.Info)
}