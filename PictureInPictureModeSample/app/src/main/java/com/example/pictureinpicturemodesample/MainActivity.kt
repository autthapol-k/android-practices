package com.example.pictureinpicturemodesample

import android.app.PendingIntent
import android.app.PictureInPictureParams
import android.app.RemoteAction
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Rect
import android.graphics.drawable.Icon
import android.os.Bundle
import android.util.Rational
import android.widget.VideoView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.net.toUri
import com.example.pictureinpicturemodesample.ui.theme.PictureInPictureModeSampleTheme

class MainActivity: ComponentActivity() {

    class PipActionReceiver: BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            println("Pip action was clicked")
        }
    }

    private val isPipSupported by lazy {
        packageManager.hasSystemFeature(PackageManager.FEATURE_PICTURE_IN_PICTURE)
    }

    private var videoViewBounds = Rect()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PictureInPictureModeSampleTheme {
                AndroidView(
                    factory = {
                        VideoView(it, null).apply {
                            setVideoURI("android.resource://$packageName/${R.raw.sample}".toUri())
                            start()
                        }
                    },
                    modifier = Modifier
                        .statusBarsPadding()
                        .fillMaxWidth()
                        .onGloballyPositioned {
                            videoViewBounds = run {
                                val boundsInWindow = it.boundsInWindow()
                                Rect(
                                    boundsInWindow.left.toInt(),
                                    boundsInWindow.top.toInt(),
                                    boundsInWindow.right.toInt(),
                                    boundsInWindow.bottom.toInt()
                                )
                            }
                        }
                )
            }
        }
    }

    private fun updatedPipParams(): PictureInPictureParams? {
        return PictureInPictureParams.Builder()
            .setSourceRectHint(videoViewBounds)
            .setAspectRatio(Rational(16, 9))
            .setActions(
                listOf(
                    RemoteAction(
                        Icon.createWithResource(
                            applicationContext,
                            R.drawable.outline_play_arrow_24
                        ),
                        "Click to play video",
                        "Play video button",
                        PendingIntent.getBroadcast(
                            applicationContext,
                            0,
                            Intent(applicationContext, PipActionReceiver::class.java),
                            PendingIntent.FLAG_IMMUTABLE
                        )
                    )
                )
            )
            .build()
    }

    override fun onUserLeaveHint() {
        super.onUserLeaveHint()
        if (!isPipSupported) {
            return
        }

        updatedPipParams()?.let {
            enterPictureInPictureMode(it)
        }
    }
}
