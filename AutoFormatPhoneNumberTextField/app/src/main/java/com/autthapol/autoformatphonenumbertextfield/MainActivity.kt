package com.autthapol.autoformatphonenumbertextfield

import android.os.Bundle
import android.telephony.PhoneNumberUtils
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.autthapol.autoformatphonenumbertextfield.ui.theme.AutoFormatPhoneNumberTextFieldTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AutoFormatPhoneNumberTextFieldTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)){
                        PhoneNumber()
                    }
                }
            }
        }
    }
}

@Composable
fun PhoneNumber(modifier: Modifier = Modifier) {
    var phoneNumber by rememberSaveable {
        mutableStateOf("")
    }
    val numbericRegex = Regex("[^0-9]")
    TextField(
        value = phoneNumber,
        onValueChange = {
//          remove non-numeric characters.
            val striped = numbericRegex.replace(it, "")
            phoneNumber = if (striped.length >= 10) {
                striped.substring(0..9)
            } else {
                striped
            }
        },
        label = { Text(text = "Enter Phone Number")},
        visualTransformation = NanpVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}
