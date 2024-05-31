package com.autthapol.compressimagesample

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import coil.compose.AsyncImage
import com.autthapol.compressimagesample.ui.theme.CompressImageSampleTheme
import java.io.ByteArrayOutputStream

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CompressImageSampleTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                ) { innerPadding ->
                    val context = LocalContext.current
                    var currentImage by remember {
                        mutableStateOf(Uri.EMPTY)
                    }
                    var compressedImage by remember {
                        mutableStateOf<Bitmap?>(null)
                    }
                    val galleryLauncher = rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.GetContent()
                    ) { uri ->
                        currentImage = uri
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {

                        Button(onClick = {
                            galleryLauncher.launch("image/*")
                        }) {
                            Text(text = "Launch Gallery")
                        }
                        AsyncImage(
                            model = currentImage,
                            contentDescription = null,
                            modifier = Modifier.size(250.dp)
                        )


                        if (currentImage != Uri.EMPTY) {
                            Button(onClick = {
                                val drawable = currentImage.toDrawable(context)
                                compressedImage = context.reduceImageSize(drawable)
                            }) {
                                Text(text = "Compress the image")
                            }

                            AsyncImage(model = compressedImage, contentDescription = null)
                        }
                    }

                }
            }
        }
    }
}

fun Uri.toDrawable(context: Context): Drawable? {
    val contentResolver = context.contentResolver
    val inputStream = contentResolver.openInputStream(this)
    return Drawable.createFromStream(inputStream, this.toString())
}

fun Context.reduceImageSize(drawable: Drawable?): Bitmap? {
    if (drawable == null) {
        return null
    }

    val baos = ByteArrayOutputStream()
    val bitmap = drawable.toBitmap(200, 200, Bitmap.Config.ARGB_8888)
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
    val imageBytes = baos.toByteArray()

    return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
}