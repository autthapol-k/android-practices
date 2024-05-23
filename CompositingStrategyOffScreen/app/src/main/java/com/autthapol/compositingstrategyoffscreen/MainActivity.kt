package com.autthapol.compositingstrategyoffscreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.autthapol.compositingstrategyoffscreen.ui.theme.CompositingStrategyOffScreenTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CompositingStrategyOffScreenTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.dog),
                            contentDescription = "dog",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(300.dp)
                                .aspectRatio(1f)
                                .background(
                                    Brush.linearGradient(
                                        listOf(
                                            Color(0xFFC5E1A5),
                                            Color(0xFF80DEEA)
                                        )
                                    )
                                )
                                .padding(8.dp)
                                .graphicsLayer {
                                    compositingStrategy = CompositingStrategy.Offscreen
                                }
                                .drawWithCache {
                                    val path = Path()
                                    path.addOval(
                                        Rect(
                                            topLeft = Offset.Zero,
                                            bottomRight = Offset(size.width, size.height)
                                        )
                                    )
                                    onDrawWithContent {
                                        clipPath(path, clipOp = ClipOp.Intersect) {
                                            this@onDrawWithContent.drawContent()
                                        }

                                        val dotSize = size.width / 8f

                                        drawCircle(
                                            Color.Black,
                                            radius = dotSize,
                                            center = Offset(
                                                x = size.width - dotSize,
                                                y = size.height - dotSize
                                            ),
                                            blendMode = BlendMode.Clear
                                        )

                                        drawCircle(
                                            Color.Red,
                                            radius = dotSize * 0.8f,
                                            center = Offset(
                                                x = size.width - dotSize,
                                                y = size.height - dotSize
                                            )
                                        )
                                    }
                                }
                        )
                    }
                }
            }
        }
    }
}