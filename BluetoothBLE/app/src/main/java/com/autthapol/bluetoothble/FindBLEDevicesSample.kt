package com.autthapol.bluetoothble

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanFilter
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.os.ParcelUuid
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.getSystemService
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.autthapol.bluetoothble.server.GATTServerSampleService.Companion.SERVICE_UUID
import kotlinx.coroutines.delay
import java.util.UUID

@SuppressLint("MissingPermission")
@Composable
fun FindBLEDevicesSample(modifier: Modifier = Modifier) {
    BluetoothBox {
        FindDevicesScreen(modifier = modifier) {
            Log.d("FindBLEDevices", "Name: ${it.name} Address: ${it.address} Type: ${it.type}")
        }
    }
}

@SuppressLint("MissingPermission")
@RequiresPermission(Manifest.permission.BLUETOOTH_SCAN)
@Composable
internal fun FindDevicesScreen(
    modifier: Modifier,
    onConnect: (BluetoothDevice) -> Unit
) {
    val context = LocalContext.current
    val bluetoothAdapter = checkNotNull(context.getSystemService<BluetoothManager>()?.adapter)
    var scanning by remember {
        mutableStateOf(true)
    }
    val devices = remember {
        mutableStateListOf<BluetoothDevice>()
    }
    val pairedDevices = remember {
        // Get a list of previously paired devices
        mutableStateListOf<BluetoothDevice>(*bluetoothAdapter.bondedDevices.toTypedArray())
    }
    val serverDevices = remember {
        mutableStateListOf<BluetoothDevice>()
    }

    // scan settings
    val scanSettings: ScanSettings =
        ScanSettings.Builder()
            .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
            .setCallbackType(ScanSettings.CALLBACK_TYPE_ALL_MATCHES)
            .setMatchMode(ScanSettings.MATCH_MODE_AGGRESSIVE)
            .setNumOfMatches(ScanSettings.MATCH_NUM_ONE_ADVERTISEMENT)
            .setReportDelay(0L)
            .build()

    // This effect will start scanning for devices when the screen is visible
    // If scanning is stop removing the effect will stop the scanning.
    if (scanning) {
        BluetoothScanEffect(scanSettings = scanSettings, onScanFailed = {
            scanning = false
            Log.w("FindBLEDevicesSample", "Scan failed with error: $it")
        }, onDeviceFound = { scanResult ->
            if (!devices.contains(scanResult.device)) {
                devices.add(scanResult.device)
            }

            // If we find out GATT server sample let's highlight it
            val serviceUUIDs = scanResult.scanRecord?.serviceUuids.orEmpty()
            // change UUID, need to match with GATT server service
            if (serviceUUIDs.contains(ParcelUuid(SERVICE_UUID))) {
                if (!serverDevices.contains(scanResult.device)) {
                    serverDevices.add(scanResult.device)
                    scanning = false
                }
            }
        })

        // Stop scanning after a while
        LaunchedEffect(key1 = true) {
            delay(30000)
            scanning = false
        }
    }

    // screen
    Column(modifier = modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Available devices", style = MaterialTheme.typography.titleSmall)
            if (scanning) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp), strokeWidth = 2.dp)
            } else {
                IconButton(onClick = {
                    devices.clear()
                    serverDevices.clear()
                    scanning = true
                }) {
                    Icon(imageVector = Icons.Rounded.Refresh, contentDescription = null)
                }
            }
        }

        LazyColumn(
            modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (devices.isEmpty()) {
                item {
                    Text(text = "No devices found")
                }
            }
            items(devices) { item ->
                BluetoothDeviceItem(
                    bluetoothDevice = item,
                    isServerDevice = serverDevices.contains(item),
                    onConnect = onConnect
                )
            }

            // paired devices list
            if (pairedDevices.isNotEmpty()) {
                item {
                    Text(text = "Saves devices", style = MaterialTheme.typography.titleSmall)
                }
                items(pairedDevices) {
                    BluetoothDeviceItem(
                        bluetoothDevice = it, onConnect = onConnect
                    )
                }
            }
        }
    }
}

@SuppressLint("MissingPermission")
@Composable
internal fun BluetoothDeviceItem(
    bluetoothDevice: BluetoothDevice,
    isServerDevice: Boolean = false,
    onConnect: (BluetoothDevice) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onConnect(bluetoothDevice) },
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = if (isServerDevice) {
                "GATT Server"
            } else {
                bluetoothDevice.name ?: "N/A"
            }, style = if (isServerDevice) {
                TextStyle(fontWeight = FontWeight.Bold)
            } else {
                TextStyle(fontWeight = FontWeight.Normal)
            }
        )
        Text(text = bluetoothDevice.address)
        val state = when (bluetoothDevice.bondState) {
            BluetoothDevice.BOND_BONDED -> "Paired"
            BluetoothDevice.BOND_BONDING -> "Pairing"
            else -> "None"
        }
        Text(text = state)
    }
}

@RequiresPermission(Manifest.permission.BLUETOOTH_SCAN)
@Composable
private fun BluetoothScanEffect(
    scanSettings: ScanSettings,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    onScanFailed: (Int) -> Unit,
    onDeviceFound: (device: ScanResult) -> Unit
) {
    val context = LocalContext.current
    val bluetoothAdapter = context.getSystemService<BluetoothManager>()?.adapter

    if (bluetoothAdapter == null) {
        onScanFailed(ScanCallback.SCAN_FAILED_INTERNAL_ERROR)
        return
    }

    val currentOnDeviceFound by rememberUpdatedState(newValue = onDeviceFound)

    DisposableEffect(key1 = lifecycleOwner, key2 = scanSettings) {
        val leScanCallback: ScanCallback = object: ScanCallback() {
            override fun onScanResult(callbackType: Int, result: ScanResult?) {
                super.onScanResult(callbackType, result)
                result?.let { currentOnDeviceFound(it) }
            }

            override fun onScanFailed(errorCode: Int) {
                super.onScanFailed(errorCode)
                onScanFailed(errorCode)
            }
        }

        val observer = LifecycleEventObserver { _, event ->
            // Start scanning once the app is in foreground and stop when in background
            if (event == Lifecycle.Event.ON_START) {
                val filters = listOf(
                    ScanFilter.Builder().setServiceUuid(ParcelUuid(SERVICE_UUID)).build()
                )

                bluetoothAdapter.bluetoothLeScanner.startScan(filters, scanSettings, leScanCallback)
            } else if (event == Lifecycle.Event.ON_STOP) {
                bluetoothAdapter.bluetoothLeScanner.stopScan(leScanCallback)
            }
        }

        // Add the observer to the lifecycle
        lifecycleOwner.lifecycle.addObserver(observer)

        // When the effect leaves the Composition, remove the observer and stop scanning
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            bluetoothAdapter.bluetoothLeScanner.stopScan(leScanCallback)
        }
    }
}