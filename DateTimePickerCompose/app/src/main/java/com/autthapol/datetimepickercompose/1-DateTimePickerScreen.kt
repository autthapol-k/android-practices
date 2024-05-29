package com.autthapol.datetimepickercompose


import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.unit.sp
import java.util.Calendar

@Composable
fun DateTimePickerScreen(modifier: Modifier = Modifier) {
    var date by remember {
        mutableStateOf("")
    }
    var time by remember {
        mutableStateOf("")
    }

    val calendar = Calendar.getInstance()
    val context = LocalContext.current

    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            date = "$dayOfMonth/${month + 1}/$year"
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH),
    )
    val timePickerDialog = TimePickerDialog(
        context,
        { _, hourOfDay, minute ->
            time = "$hourOfDay:$minute"
        },
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        false
    )

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            datePickerDialog.show()
        }) {
            Text(text = "Pick Date")
        }
        Text(
            text = "Selected Date: $date",
            fontSize = 20.sp,
            modifier = Modifier.padding(top = 8.dp)
        )

        Button(onClick = {
            timePickerDialog.show()
        }) {
            Text(text = "Pick Time")
        }
        Text(
            text = "Selected Time: $time",
            fontSize = 20.sp,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}