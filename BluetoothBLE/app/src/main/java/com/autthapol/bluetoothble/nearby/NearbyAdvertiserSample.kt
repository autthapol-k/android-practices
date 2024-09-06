package com.autthapol.bluetoothble.nearby

import android.Manifest
import android.os.Build
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.autthapol.bluetoothble.BluetoothBox
import com.google.android.gms.nearby.Nearby
import com.google.android.gms.nearby.connection.AdvertisingOptions
import com.google.android.gms.nearby.connection.ConnectionInfo
import com.google.android.gms.nearby.connection.ConnectionLifecycleCallback
import com.google.android.gms.nearby.connection.ConnectionResolution
import com.google.android.gms.nearby.connection.Strategy

@Composable
fun NearbyAdvertiserSample(modifier: Modifier) {
    val extraPermissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        setOf(Manifest.permission.BLUETOOTH_ADVERTISE)
    } else {
        emptySet()
    }

    BluetoothBox(extraPermissions = extraPermissions) { adapter ->
        if (adapter.isMultipleAdvertisementSupported) {
            NearbyAdvertiserScreen(modifier = modifier)
        } else {
            Text(text = "Cannot run server:\nDevices does not support multi-advertisement")
        }
    }
}

@Composable
internal fun NearbyAdvertiserScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val client = Nearby.getConnectionsClient(context)
    val advertisingOptions = AdvertisingOptions.Builder()
        .setStrategy(Strategy.P2P_STAR)
        .build()

    var status by remember {
        mutableStateOf("N/A")
    }

    var advertising by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                advertising = !advertising
                if (advertising) {
                    client.startAdvertising(
                        "Wireless Attendance",
                        "_6D5CCAE417E7._tcp",
                        object: ConnectionLifecycleCallback() {
                            override fun onConnectionInitiated(p0: String, p1: ConnectionInfo) {
                            }

                            override fun onConnectionResult(p0: String, p1: ConnectionResolution) {
                            }

                            override fun onDisconnected(p0: String) {
                            }
                        },
                        advertisingOptions
                    ).addOnSuccessListener {
                        status = "Advertising..."
                    }.addOnFailureListener {
                        status = "Stopped"
                        advertising = false
                    }
                } else {
                    client.stopAdvertising()
                    status = "Stopped"
                    advertising = false
                }
            }
        ) {
            Text(text = if (advertising) "Stop Advertising..." else "Start Advertising...")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Status: $status")
    }
}