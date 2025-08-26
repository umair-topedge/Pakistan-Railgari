package com.example.pakistanrailgari.alarm.data

import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class Alarm(
    val id: String = UUID.randomUUID().toString(),
    val hour: Int,
    val minute: Int,
    val isEnabled: Boolean = true,
    val createdAt: Long = System.currentTimeMillis()
) {
    val timeInMillis: Long
        get() {
            val calendar = java.util.Calendar.getInstance().apply {
                set(java.util.Calendar.HOUR_OF_DAY, hour)
                set(java.util.Calendar.MINUTE, minute)
                set(java.util.Calendar.SECOND, 0)
                set(java.util.Calendar.MILLISECOND, 0)
                
                // If the time has already passed today, set for tomorrow
                if (timeInMillis <= System.currentTimeMillis()) {
                    add(java.util.Calendar.DAY_OF_MONTH, 1)
                }
            }
            return calendar.timeInMillis
        }
}