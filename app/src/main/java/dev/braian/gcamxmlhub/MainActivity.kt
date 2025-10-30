package dev.braian.gcamxmlhub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import dev.braian.gcamxmlhub.data.navigation.Navigation
import dev.braian.gcamxmlhub.ui.theme.GcamXMLHubTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GcamXMLHubTheme {
                Navigation()
               


            }
        }
    }
}
