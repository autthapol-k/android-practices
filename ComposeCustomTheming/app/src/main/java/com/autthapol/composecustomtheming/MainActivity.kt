package com.autthapol.composecustomtheming

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import com.autthapol.composecustomtheming.ui.spacing
import com.autthapol.composecustomtheming.ui.theme.ComposeCustomThemingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeCustomThemingTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background,
                    modifier = Modifier.padding(MaterialTheme.spacing.extraLarge)
                ) {
                    Text(text = "Hello World!")
                }
            }
        }
    }
}