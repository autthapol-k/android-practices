package com.autthapol.sharingdatabetweenscreens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.autthapol.sharingdatabetweenscreens.content.AppRoot
import com.autthapol.sharingdatabetweenscreens.content.NavigationArgsSample
import com.autthapol.sharingdatabetweenscreens.content.PersistentStorageSample
import com.autthapol.sharingdatabetweenscreens.content.SharedViewModelSample
import com.autthapol.sharingdatabetweenscreens.content.StatefulDependencySample
import com.autthapol.sharingdatabetweenscreens.ui.theme.SharingDataBetweenScreensTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SharingDataBetweenScreensTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
//                    NavigationArgsSample()
//                    SharedViewModelSample()
//                    StatefulDependencySample()
//                    AppRoot()
                    PersistentStorageSample()
                }
            }
        }
    }
}