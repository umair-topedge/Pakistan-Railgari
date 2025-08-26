package com.example.pakistanrailgari.alarm.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pakistanrailgari.alarm.viewmodel.AlarmViewModel
import com.example.pakistanrailgari.ui.theme.Purple40
import com.example.pakistanrailgari.ui.theme.Purple80
import network.chaintech.sdpcomposemultiplatform.sdp

@Composable
fun AlarmScreen(
    viewModel: AlarmViewModel = viewModel()
) {
    val context = LocalContext.current
    val selectedHour = viewModel.selectedHour
    val selectedMinute = viewModel.selectedMinute
    val errorMessage = viewModel.errorMessage
    val successMessage = viewModel.successMessage
    var showTimePicker by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        // Initialize with current time
        val calendar = java.util.Calendar.getInstance()
        viewModel.updateSelectedTime(
            calendar.get(java.util.Calendar.HOUR_OF_DAY),
            calendar.get(java.util.Calendar.MINUTE)
        )
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Purple40
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.sdp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Set Alarm",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Purple80
            )

            Spacer(modifier = Modifier.height(32.sdp))

            Button(
                onClick = { showTimePicker = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.sdp)
            ) {
                Text(
                    text = formatTime(selectedHour, selectedMinute),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(32.sdp))

            Button(
                onClick = { viewModel.setAlarm() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.sdp),
                enabled = !viewModel.isAlarmSet
            ) {
                Text(
                    text = "Set Alarm",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.sdp))

            errorMessage?.let { message ->
                Text(
                    text = message,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            successMessage?.let { message ->
                Text(
                    text = message,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            if (showTimePicker) {
                TimePickerDialog(
                    initialHour = selectedHour,
                    initialMinute = selectedMinute,
                    onTimeSelected = { hour, minute ->
                        viewModel.updateSelectedTime(hour, minute)
                        showTimePicker = false
                    },
                    onDismiss = { showTimePicker = false }
                )
            }
        }
    }
}

@Composable
fun TimePickerDialog(
    initialHour: Int,
    initialMinute: Int,
    onTimeSelected: (Int, Int) -> Unit,
    onDismiss: () -> Unit
) {
    var hour by remember { mutableIntStateOf(initialHour) }
    var minute by remember { mutableIntStateOf(initialMinute) }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.sdp),
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier.padding(16.sdp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Select Time",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.sdp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                // Hour picker
                NumberPicker(
                    value = hour,
                    onValueChange = { hour = it },
                    range = 0..23,
                    modifier = Modifier.weight(1f)
                )

                Text(
                    text = ":",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )

                // Minute picker
                NumberPicker(
                    value = minute,
                    onValueChange = { minute = it },
                    range = 0..59,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(16.sdp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = onDismiss) {
                    Text("Cancel")
                }

                Button(onClick = { onTimeSelected(hour, minute) }) {
                    Text("OK")
                }
            }
        }
    }
}

@Composable
fun NumberPicker(
    value: Int,
    onValueChange: (Int) -> Unit,
    range: IntRange,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Button(onClick = {
            val newValue = if (value < range.last) value + 1 else range.first
            onValueChange(newValue)
        }) {
            Text("+")
        }

        Text(
            text = if (value < 10) "0$value" else "$value",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(vertical = 8.sdp)
                .width(60.sdp)
        )

        Button(onClick = {
            val newValue = if (value > range.first) value - 1 else range.last
            onValueChange(newValue)
        }) {
            Text("-")
        }
    }
}

private fun formatTime(hour: Int, minute: Int): String {
    val hourString =
        if (hour == 0) "12" else if (hour > 12) (hour - 12).toString() else hour.toString()
    val minuteString = if (minute < 10) "0$minute" else minute.toString()
    val amPm = if (hour < 12) "AM" else "PM"
    return "$hourString:$minuteString $amPm"
}