package com.autthapol.datesforandroidapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.autthapol.datesforandroidapp.ui.theme.DatesForAndroidAppTheme
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DatesForAndroidAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    val date = remember {
//                        LocalTime.now()
//                        LocalDate.now()
//                        LocalDateTime.now()
//                        LocalDateTime.of(2022,11,10,10,20,5)
//                        ZonedDateTime.now()
//                            .toEpochSecond() * 1000
                        ZonedDateTime.ofInstant(
                            Instant.ofEpochMilli(System.currentTimeMillis()),
//                            ZoneId.of("Asia/Tokyo")
                            ZoneId.systemDefault()
                        )
                    }

                    val formatDateTime = remember {
                        DateTimeFormatter.ofPattern("EEE dd MM yyyy HH:mm:ss").format(date)
                    }

                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//                        Text(text = date.toString())
                        Text(text = formatDateTime)
                    }
                }
            }
        }
    }
}