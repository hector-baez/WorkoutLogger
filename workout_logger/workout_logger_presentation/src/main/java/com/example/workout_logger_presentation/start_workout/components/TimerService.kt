package com.example.workout_logger_presentation.start_workout.components

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.workout_logger_presentation.start_workout.StartWorkoutViewModel
import com.hbaez.workout_logger_presentation.R

class TimerService: Service() {

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    private val CHANNEL_ID = "ForegroundService Kotlin"

    companion object {
        private var isRunning: Boolean = false
    }

    override fun onDestroy() {
        isRunning = false
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        isRunning = true
        val input = intent?.getStringExtra("inputExtra")
        createNotificationChannel()

        val mChannel = NotificationChannel(CHANNEL_ID, "Notifications", NotificationManager.IMPORTANCE_MIN)
        mChannel.enableLights(false)
        mChannel.vibrationPattern = longArrayOf(0L)
        mChannel.enableVibration(true)

        val notificationIntent = Intent(this, StartWorkoutViewModel::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0, notificationIntent, PendingIntent.FLAG_IMMUTABLE
        )
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Rest Timer Foreground Service")
            .setContentText(input)
            .setSmallIcon(R.drawable.ic_exercise)
            .setContentIntent(pendingIntent)
            .build()
        startForeground(1, notification)

        return START_STICKY
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val serviceChannel = NotificationChannel(CHANNEL_ID, "Foreground Service Channel",
            NotificationManager.IMPORTANCE_DEFAULT)
        val manager = getSystemService(NotificationManager::class.java)
        manager!!.createNotificationChannel(serviceChannel)
    }

}