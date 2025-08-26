package com.example.pakistanrailgari.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.pakistanrailgari.alarm.data.AlarmRepository
import com.example.pakistanrailgari.alarm.utils.AlarmUtils

class AlarmReceiver : BroadcastReceiver() {
    
    override fun onReceive(context: Context, intent: Intent) {
        val alarmId = intent.getStringExtra("ALARM_ID") ?: return
        
        // Get the alarm from repository
        val repository = AlarmRepository.getInstance(context)
        val alarm = repository.getAlarm(alarmId) ?: return
        
        // Start the alarm service
        val serviceIntent = Intent(context, AlarmService::class.java).apply {
            putExtra("ALARM_ID", alarmId)
        }
        
        // Show notification
        AlarmUtils.showNotification(context, alarmId)
        
        // Start the alarm service
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            context.startForegroundService(serviceIntent)
        } else {
            context.startService(serviceIntent)
        }
    }
}