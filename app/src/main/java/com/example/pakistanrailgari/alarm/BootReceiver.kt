package com.example.pakistanrailgari.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.pakistanrailgari.alarm.data.AlarmRepository
import com.example.pakistanrailgari.alarm.utils.AlarmUtils

class BootReceiver : BroadcastReceiver() {
    
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            // Reschedule all alarms
            val repository = AlarmRepository.getInstance(context)
            val alarms = repository.getAllAlarms()
            
            for (alarm in alarms) {
                if (alarm.isEnabled) {
                    AlarmUtils.scheduleAlarm(context, alarm)
                }
            }
        }
    }
}