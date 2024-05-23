package com.autthapol.sharedpreferencesdelegate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.autthapol.sharedpreferencesdelegate.ui.theme.SharedPreferencesDelegateTheme

class MainActivity : ComponentActivity() {

    private var token by sharedPreferences("token", defaultValue = "")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        token = "Hello World!"

        setContent {
            SharedPreferencesDelegateTheme {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = token ?: "No token")
                }
            }
        }
    }
}