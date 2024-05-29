package com.autthapol.datetimepickercompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.autthapol.datetimepickercompose.ui.theme.DateTimePickerComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DateTimePickerComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    DateTimePickerScreen(modifier = Modifier.padding(innerPadding))
//                    DateTimePickerVanpraScreen(modifier = Modifier.padding(innerPadding))
                    DateTimePickerMaterial3Screen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}