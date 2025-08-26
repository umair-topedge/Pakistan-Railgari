package com.example.pakistanrailgari.alarm

import android.app.Notification
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.pakistanrailgari.R

class AlarmForegroundService : Service() {
    
    companion object {
        private const val NOTIFICATION_ID = 1001
        private const val CHANNEL_ID = "ALARM_SERVICE_CHANNEL"
    }
    
    override fun onBind(intent: Intent): IBinder? {
        return null
    }
    
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }
    
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(NOTIFICATION_ID, createNotification())
        return START_NOT_STICKY
    }
    
    private fun createNotification(): Notification {
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Alarm Service")
            .setContentText("Running alarm service in the background")
            .setSmallIcon(R.drawable.ic_launcher_foreground) // Replace with your actual icon
            .build()
    }
    
    private fun createNotificationChannel() {
        // Notification channel is created in AlarmApplication
    }
}