package com.example.pakistanrailgari.alarm.data

import android.content.Context
import android.content.SharedPreferences
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class AlarmRepository private constructor(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("alarms", Context.MODE_PRIVATE)
    private val json = Json { ignoreUnknownKeys = true }
    
    companion object {
        @Volatile
        private var INSTANCE: AlarmRepository? = null
        
        fun getInstance(context: Context): AlarmRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: AlarmRepository(context).also { INSTANCE = it }
            }
        }
    }
    
    fun saveAlarm(alarm: Alarm) {
        val alarms = getAllAlarms().toMutableList()
        alarms.removeAll { it.id == alarm.id }
        alarms.add(alarm)
        
        prefs.edit()
            .putString("alarms", json.encodeToString(alarms))
            .apply()
    }
    
    fun getAlarm(id: String): Alarm? {
        return getAllAlarms().find { it.id == id }
    }
    
    fun getAllAlarms(): List<Alarm> {
        val alarmsString = prefs.getString("alarms", "[]") ?: "[]"
        return try {
            json.decodeFromString<List<Alarm>>(alarmsString)
        } catch (e: Exception) {
            emptyList()
        }
    }
    
    fun deleteAlarm(id: String) {
        val alarms = getAllAlarms().filter { it.id != id }
        prefs.edit()
            .putString("alarms", json.encodeToString(alarms))
            .apply()
    }
    
    fun updateAlarm(alarm: Alarm) {
        saveAlarm(alarm)
    }
}