package com.example.pakistanrailgari.alarm.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.pakistanrailgari.alarm.data.Alarm
import com.example.pakistanrailgari.alarm.data.AlarmRepository
import com.example.pakistanrailgari.alarm.utils.AlarmUtils
import kotlinx.coroutines.launch

class AlarmViewModel(application: Application) : AndroidViewModel(application) {
    
    private val repository = AlarmRepository.getInstance(application)
    
    var selectedHour by mutableStateOf(0)
        private set
        
    var selectedMinute by mutableStateOf(0)
        private set
        
    var isAlarmSet by mutableStateOf(false)
        private set
        
    var errorMessage by mutableStateOf<String?>(null)
        private set
        
    var successMessage by mutableStateOf<String?>(null)
        private set
    
    fun updateSelectedTime(hour: Int, minute: Int) {
        selectedHour = hour
        selectedMinute = minute
    }
    
    fun setAlarm() {
        viewModelScope.launch {
            try {
                // Check if we can schedule exact alarms
                if (!AlarmUtils.canScheduleExactAlarms(getApplication())) {
                    errorMessage = "Cannot schedule exact alarms. Please grant permission in settings."
                    return@launch
                }
                
                // Create alarm object
                val alarm = Alarm(
                    hour = selectedHour,
                    minute = selectedMinute
                )
                
                // Save to repository
                repository.saveAlarm(alarm)
                
                // Schedule the alarm
                AlarmUtils.scheduleAlarm(getApplication(), alarm)
                
                // Update state
                isAlarmSet = true
                successMessage = "Alarm set for ${formatTime(selectedHour, selectedMinute)}"
                errorMessage = null
            } catch (e: Exception) {
                errorMessage = "Failed to set alarm: ${e.message}"
                successMessage = null
            }
        }
    }
    
    fun clearMessages() {
        errorMessage = null
        successMessage = null
    }
    
    private fun formatTime(hour: Int, minute: Int): String {
        val hourString = if (hour == 0) "12" else if (hour > 12) (hour - 12).toString() else hour.toString()
        val minuteString = if (minute < 10) "0$minute" else minute.toString()
        val amPm = if (hour < 12) "AM" else "PM"
        return "$hourString:$minuteString $amPm"
    }
}