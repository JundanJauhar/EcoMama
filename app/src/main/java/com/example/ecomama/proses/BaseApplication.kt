package com.example.ecomama

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build

class BaseApplication : Application() {

    // ID saluran notifikasi
    companion object {
        const val CHANNEL_1_ID = "channel_1"
        const val CHANNEL_2_ID = "channel_2"
    }

    override fun onCreate() {
        super.onCreate()

        // Menginisialisasi saluran notifikasi
        createNotificationChannels()
    }

    // Fungsi untuk membuat saluran notifikasi
    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = getSystemService(NotificationManager::class.java)

            // Saluran notifikasi 1 (High Priority)
            val channel1 = NotificationChannel(
                CHANNEL_1_ID,
                "High Priority Notifications",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "This channel is used for high priority notifications."
            }

            // Saluran notifikasi 2 (Low Priority)
            val channel2 = NotificationChannel(
                CHANNEL_2_ID,
                "Low Priority Notifications",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "This channel is used for low priority notifications."
            }

            // Menambahkan saluran ke NotificationManager
            notificationManager.createNotificationChannels(listOf(channel1, channel2))
        }
    }
}
