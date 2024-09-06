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
import com.google.android.gms.nearby.connection.DiscoveredEndpointInfo
import com.google.android.gms.nearby.connection.DiscoveryOptions
import com.google.android.gms.nearby.connection.EndpointDiscoveryCallback
import com.google.android.gms.nearby.connection.Strategy


@Composable
fun NearbyDiscoverySample(modifier: Modifier) {
    val extraPermissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        setOf(Manifest.permission.BLUETOOTH_ADVERTISE)
    } else {
        emptySet()
    }

    BluetoothBox(extraPermissions = extraPermissions) { adapter ->
        if (adapter.isMultipleAdvertisementSupported) {
            NearbyDiscoveryScreen(modifier = modifier)
        } else {
            Text(text = "Cannot run server:\nDevices does not support multi-advertisement")
        }
    }
}

@Composable
internal fun NearbyDiscoveryScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val client = Nearby.getConnectionsClient(context)
    val discoveryOptions = DiscoveryOptions.Builder()
        .setStrategy(Strategy.P2P_STAR)
        .build()

    var status by remember {
        mutableStateOf("N/A")
    }
    var endpointInfo by remember {
        mutableStateOf<DiscoveredEndpointInfo?>(null)
    }

    var discovering by remember {
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
                discovering = !discovering
                if (discovering) {
                    client.startDiscovery(
                        "_6D5CCAE417E7._tcp",
                        object: EndpointDiscoveryCallback() {
                            override fun onEndpointFound(p0: String, p1: DiscoveredEndpointInfo) {
                                endpointInfo = p1
                            }

                            override fun onEndpointLost(p0: String) {

                            }
                        },
                        discoveryOptions
                    ).addOnSuccessListener {
                        status = "Discovering..."
                    }.addOnFailureListener {
                        status = "Stopped"
                        discovering = false
                    }

                } else {
                    client.stopDiscovery()
                    status = "Stopped"
                    endpointInfo = null
                    discovering = false
                }
            }
        ) {
            Text(text = if (discovering) "Stop Discovery..." else "Start Discovery...")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Status: $status")
        endpointInfo?.let {
            Text(text = "Founded Endpoint Name: ${endpointInfo?.endpointName}")
            Text(text = "Founded Service Id: ${endpointInfo?.serviceId}")
        }
    }
}