package com.autthapol.bluetoothble

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.autthapol.bluetoothble.nearby.NearbyAdvertiserSample
import com.autthapol.bluetoothble.nearby.NearbyDiscoverySample
import com.autthapol.bluetoothble.server.GATTServerSample
import com.autthapol.bluetoothble.ui.theme.BluetoothBLETheme

class MainActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BluetoothBLETheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    FindBLEDevicesSample(modifier = Modifier.padding(innerPadding))
//                    GATTServerSample(modifier = Modifier.padding(innerPadding))

//                    NearbyAdvertiserSample(modifier = Modifier.padding(innerPadding))
//                    NearbyDiscoverySample(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}