package com.example.lesson1

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat

class PlaybackService : Service() {
    private val CHANNEL_ID = "MyPlaybackServiceChannel"
    private val NOTIFICATION_ID = 1234
    private val TAG = "PlaybackService"

    private lateinit var notificationManager: NotificationManager

    companion object {
        const val ACTION_PLAY = "com.example.lesson1.action.PLAY"
        const val ACTION_PAUSE = "com.example.lesson1.action.PAUSE"
        const val ACTION_STOP = "com.example.lesson1.action.STOP"
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate")
        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        // Create a notification channel for the foreground service
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "MyForegroundServiceChannel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand")
        val flag = PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("Playback Service")
            .setContentText("Running playback service")
            .setContentIntent(
                PendingIntent.getActivity(
                    this,
                    0,
                    Intent(this, MainActivity::class.java),
                    flag
                )
            )
            .addAction(
                R.drawable.ic_play,
                "Play",
                PendingIntent.getBroadcast(this, 0, Intent(ACTION_PLAY), flag)
            )
            .addAction(
                R.drawable.ic_pause,
                "Pause",
                PendingIntent.getBroadcast(this, 0, Intent(ACTION_PAUSE), flag)
            )
            .addAction(
                R.drawable.ic_stop,
                "Stop",
                PendingIntent.getBroadcast(this, 0, Intent(ACTION_STOP), flag)
            )
            .setOnlyAlertOnce(true)
            .build()

        startForeground(NOTIFICATION_ID, notification)

        // Register a BroadcastReceiver to handle custom broadcast intents
        registerReceiver(
            notificationControlsReceiver,
            IntentFilter().apply {
                addAction(ACTION_PLAY)
                addAction(ACTION_PAUSE)
                addAction(ACTION_STOP)
            }
        )

        // Do some work in the foreground service...

        return START_STICKY
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy")
        unregisterReceiver(notificationControlsReceiver)
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private val notificationControlsReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (context == null) return
            Log.d(TAG, "Received action ${intent?.action}")

            when (intent?.action) {
                ACTION_PLAY -> {
                    // Handle play button click
                    // ...
                    Log.d(TAG, "Action PLAY")
                }
                ACTION_PAUSE -> {
                    // Handle pause button click
                    // ...
                    Log.d(TAG, "Action PAUSE")
                }
                ACTION_STOP -> {
                    // Handle stop button click
                    // ...
                    Log.d(TAG, "Action STOP")
                    stopForeground(STOP_FOREGROUND_REMOVE)
                    stopSelf()
                }
            }
        }
    }
}
