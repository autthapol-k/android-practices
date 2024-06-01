package com.autthapol.broadcastreceiver

import android.annotation.SuppressLint
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
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
import com.autthapol.broadcastreceiver.ui.theme.BroadCastReceiverTheme

class MainActivity : ComponentActivity() {

    private val airPlaneModeBroadCastReceiver = AirPlaneModeBroadCastReceiver()
    private val testActionBroadCastReceiver = TestActionBroadCastReceiver()
    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        registerReceiver(
            airPlaneModeBroadCastReceiver,
            IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(
                testActionBroadCastReceiver,
                IntentFilter("TEST_ACTION"),
                RECEIVER_EXPORTED
            )
        }else{
            registerReceiver(
                testActionBroadCastReceiver,
                IntentFilter("TEST_ACTION")
            )
        }

        setContent {
            BroadCastReceiverTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(airPlaneModeBroadCastReceiver)
        unregisterReceiver(testActionBroadCastReceiver)
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BroadCastReceiverTheme {
        Greeting("Android")
    }
}