package com.autthapol.animatemotionlayoutcomposeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionScene
import androidx.constraintlayout.compose.layoutId
import com.autthapol.animatemotionlayoutcomposeapp.ui.theme.AnimateMotionLayoutComposeAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnimateMotionLayoutComposeAppTheme {
                Column {
                    var progress by remember {
                        mutableStateOf(0f)
                    }
                    ProfileHeader(progress = progress)
                    Spacer(modifier = Modifier.height(32.dp))
                    Slider(
                        value = progress,
                        onValueChange = {
                            progress = it
                        },
                        modifier = Modifier.padding(
                            horizontal = 32.dp
                        )
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMotionApi::class)
@Composable
fun ProfileHeader(progress: Float) {
    val context = LocalContext.current
    val motionScene = remember {
        context.resources.openRawResource(R.raw.motion_scene).readBytes().decodeToString()
    }

    MotionLayout(
        motionScene = MotionScene(content = motionScene),
        progress = progress,
        modifier = Modifier.fillMaxWidth()
    ) {

        val properties = motionProperties(id = "profile_pic")
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.DarkGray)
                .layoutId("box")
        )
        Image(
            painter = painterResource(id = R.drawable.photo),
            contentDescription = null,
            colorFilter = ColorFilter.tint(properties.value.color("background")),
            modifier = Modifier
                .clip(CircleShape)
                .border(
                    width = 2.dp,
                    color = properties.value.color("background"),
                    shape = CircleShape
                )
                .layoutId("profile_pic")
        )
        Text(
            text = "John Doe",
            fontSize = 24.sp,
            color = properties.value.color("background"),
            modifier = Modifier.layoutId("username")
        )
    }
}