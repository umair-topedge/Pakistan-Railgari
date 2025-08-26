package com.example.pakistanrailgari.alarm

import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import android.os.IBinder
import android.os.Vibrator
import com.example.pakistanrailgari.alarm.data.AlarmRepository

class AlarmService : Service() {
    
    private var mediaPlayer: MediaPlayer? = null
    private var vibrator: Vibrator? = null
    
    override fun onBind(intent: Intent): IBinder? {
        return null
    }
    
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val alarmId = intent?.getStringExtra("ALARM_ID") ?: return START_NOT_STICKY
        
        // Get alarm details
        val repository = AlarmRepository.getInstance(this)
        val alarm = repository.getAlarm(alarmId)
        
        if (alarm != null) {
            // Play alarm sound
            playAlarmSound()
            
            // Vibrate
            vibrate()
        }
        
        // For testing purposes, we'll stop the service after 10 seconds
        // In a real app, you would have a proper UI to stop the alarm
        android.os.Handler().postDelayed({
            stopSelf()
        }, 10000) // Stop after 10 seconds
        
        return START_NOT_STICKY
    }
    
    private fun playAlarmSound() {
        try {
            val alarmUri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
            mediaPlayer = MediaPlayer().apply {
                setDataSource(this@AlarmService, alarmUri)
                prepareAsync()
                setOnPreparedListener { start() }
                isLooping = true
            }
        } catch (e: Exception) {
            // Fallback to notification sound
            try {
                val notificationUri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                mediaPlayer = MediaPlayer().apply {
                    setDataSource(this@AlarmService, notificationUri)
                    prepareAsync()
                    setOnPreparedListener { start() }
                    isLooping = true
                }
            } catch (e2: Exception) {
                e2.printStackTrace()
            }
        }
    }
    
    private fun vibrate() {
        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        val pattern = longArrayOf(0, 1000, 1000, 1000, 1000, 1000)
        
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            vibrator?.vibrate(android.os.VibrationEffect.createWaveform(pattern, 0))
        } else {
            @Suppress("DEPRECATION")
            vibrator?.vibrate(pattern, 0)
        }
    }
    
    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.apply {
            if (isPlaying) stop()
            release()
        }
        mediaPlayer = null
        
        vibrator?.cancel()
        vibrator = null
    }
}