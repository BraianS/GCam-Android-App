package dev.braian.gcamxmlhub.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.braian.gcamxmlhub.ui.theme.GcamXMLHubTheme

@Composable
 fun MenuButton(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit,
    isLogout: Boolean = false
) {
    val buttonColor = if (isLogout) {
        Color.Red
    } else {
        MaterialTheme.colorScheme.secondary
    }

    val contentColor = if (isLogout) {
        Color.White
    } else {
        MaterialTheme.colorScheme.onSecondary
    }

    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = MaterialTheme.shapes.medium,
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColor,
            contentColor = contentColor
        )
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = text, fontSize = 16.sp, modifier = Modifier.padding(8.dp))
        }
    }
}

@Composable
@Preview
fun MenuButtonPreview() {
    GcamXMLHubTheme() {
       MenuButton(
           text = "Subscribe",
           icon = Icons.Default.Star,
           onClick = { /* Handle click */ },
           isLogout = false
       )

    }
}