package com.autthapol.datetimepickercompose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateTimePickerMaterial3Screen(modifier: Modifier = Modifier) {

    val now = ZonedDateTime.now(ZoneId.of("UTC+07:00"))
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = now.toInstant().toEpochMilli()
    )
    var selectedDate by remember {
        mutableStateOf<Long?>(null)
    }
    val selectedDateLabel = remember {
        derivedStateOf {
            selectedDate?.convertMillisToDate() ?: ""
        }
    }
    var openDatePickerDialog by remember {
        mutableStateOf(false)
    }
    val calendarPickerMainColor = Color(0xFF722276)

    var openTimePickerDialog by remember {
        mutableStateOf(false)
    }
    var selectedTimeLabel by remember {
        mutableStateOf("")
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = {
            openDatePickerDialog = !openDatePickerDialog
        }) {
            Text(text = "Open Date Picker")
        }
        Text(text = "Selected Date: ${selectedDateLabel.value}")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            openTimePickerDialog = !openTimePickerDialog
        }) {
            Text(text = "Open Time Picker")
        }
        Text(text = "Selected Time: $selectedTimeLabel")
    }

    if (openDatePickerDialog) {
        DatePickerDialog(
            onDismissRequest = {
                openDatePickerDialog = false
            },
            confirmButton = {
                TextButton(onClick = {
                    openDatePickerDialog = false
                    selectedDate = datePickerState.selectedDateMillis
                }) {
                    Text(text = "OK", color = calendarPickerMainColor)
                }
            },
            dismissButton = {
                TextButton(onClick = { openDatePickerDialog = false }) {
                    Text(text = "CANCEL", color = calendarPickerMainColor)
                }
            }
        ) {
            // The actual DatePicker component within the dialog
            DatePicker(
                state = datePickerState,
                colors = DatePickerDefaults.colors(
                    selectedDayContainerColor = calendarPickerMainColor,
                    selectedDayContentColor = Color.White,
                    selectedYearContainerColor = calendarPickerMainColor,
                    selectedYearContentColor = Color.White,
                    todayContentColor = calendarPickerMainColor,
                    todayDateBorderColor = calendarPickerMainColor
                )
            )
        }
    }
    if (openTimePickerDialog) {
        TimePickerDialog(onCancel = {
            openTimePickerDialog = false
        }, onConfirm = {
            selectedTimeLabel = "${it.get(Calendar.HOUR_OF_DAY)}: ${it.get(Calendar.MINUTE)}"
            openTimePickerDialog = false
        })
    }
}

fun Long.convertMillisToDate(): String {
    // Create a calendar instance in the default time zone
    val calender = Calendar.getInstance().apply {
        timeInMillis = this@convertMillisToDate
        // Adjust for the time zone offset to get the correct local date
        val zoneOffset = get(Calendar.ZONE_OFFSET)
        val dstOffset = get(Calendar.DST_OFFSET)
        add(Calendar.MILLISECOND, -(zoneOffset + dstOffset))
    }
    // Format the calendar time in the specified format
    val sdf = SimpleDateFormat("MMM dd, yyyy", Locale.US)
    return sdf.format(calender.time)
}