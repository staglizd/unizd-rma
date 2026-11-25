package org.unizd.rma.putopis

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import org.unizd.rma.putopis.presentation.navigation.AppNavigation
import org.unizd.rma.putopis.ui.theme.PutopisTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PutopisTheme {
                AppNavigation()
            }
        }
    }
}
