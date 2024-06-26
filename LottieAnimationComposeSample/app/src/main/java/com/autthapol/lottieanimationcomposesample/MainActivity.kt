package com.autthapol.lottieanimationcomposesample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.autthapol.lottieanimationcomposesample.ui.theme.LottieAnimationComposeSampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LottieAnimationComposeSampleTheme {
                val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.heart))
                var isPlaying by remember {
                    mutableStateOf(true)
                }
                val progress by animateLottieCompositionAsState(
                    composition = composition,
//                    isPlaying = isPlaying
                    iterations = LottieConstants.IterateForever
                )

                LaunchedEffect(key1 = progress) {
                    if (progress == 0f) {
                        isPlaying = true
                    }
                    if (progress == 1f) {
                        isPlaying = false
                    }
                }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        LottieAnimation(
                            modifier = Modifier.size(400.dp),
                            composition = composition,
                            progress = { progress },

                            )
                        Button(onClick = {
                            isPlaying = true
                        }) {
                            Text(text = "Play Again")
                        }
                    }
                }
            }
        }
    }
}

