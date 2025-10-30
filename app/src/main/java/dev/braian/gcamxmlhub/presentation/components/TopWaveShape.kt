package dev.braian.gcamxmlhub.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TopWaveShape() {
    val colorScheme = MaterialTheme.colorScheme

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val path = Path().apply {
                moveTo(0f, 0f)
                lineTo(size.width, 0f)
                lineTo(size.width, size.height * 0.7f)
                quadraticTo(
                    x1 = size.width * 0.75f,
                    y1 = size.height * 1.0f,
                    x2 = 0f,
                    y2 = size.height * 0.7f
                )
                close()
            }
            drawPath(path, color = colorScheme.secondary)
        }
    }
}

@Composable
@Preview(showBackground = true)
fun TopWaveShapePreview() {
    TopWaveShape()
}