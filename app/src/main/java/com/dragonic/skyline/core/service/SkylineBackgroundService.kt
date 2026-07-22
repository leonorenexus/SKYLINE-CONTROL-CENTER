package com.dragonic.skyline.core.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.dragonic.skyline.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SkylineBackgroundService : Service() {

    companion object {
        const val CHANNEL_ID = "skyline_bg_service"
        const val NOTIF_ID   = 1001
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        startForeground(NOTIF_ID, buildNotification())
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun buildNotification(): Notification =
        NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Skyline Control Center")
            .setContentText("Sinkronisasi berjalan di latar belakang")
            .setSmallIcon(android.R.drawable.ic_menu_compass)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            "Skyline Background Sync",
            NotificationManager.IMPORTANCE_LOW
        )
        getSystemService(NotificationManager::class.java)?.createNotificationChannel(channel)
    }
}
