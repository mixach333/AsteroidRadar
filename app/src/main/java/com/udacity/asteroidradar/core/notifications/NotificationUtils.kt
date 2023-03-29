package com.udacity.asteroidradar.core.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.presetnation.MainActivity
import android.app.PendingIntent as PendingIntent1

private const val NOTIFICATION_ID = 0

fun NotificationManager.sendNotification(messageBody: String, applicationContext: Context) {
    val notificationIntent = Intent(applicationContext, MainActivity::class.java)
    val pendingIntent = PendingIntent1.getActivity(
        applicationContext,
        NOTIFICATION_ID,
        notificationIntent,
        PendingIntent1.FLAG_IMMUTABLE or PendingIntent1.FLAG_UPDATE_CURRENT
    )

    val builder = NotificationCompat.Builder(
        applicationContext,
        applicationContext.getString(R.string.notification_channel_asteroids_update_id)
    )
        .setSmallIcon(R.drawable.ic_status_normal)
        .setContentTitle(applicationContext.getString(R.string.asteroids_notification_title))
        .setContentText(messageBody)
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)

    notify(NOTIFICATION_ID, builder.build())
}

fun NotificationManager.createAsteroidsUpdateChannel(channelId: String, channelName: String) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val notificationChannel = NotificationChannel(
            channelId,
            channelName,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            enableLights(true)
            lightColor = Color.RED
            enableVibration(true)
            description = "Network Update"
        }
        createNotificationChannel(notificationChannel)
    }
}

fun NotificationManager.createFirebaseUpdateChannel(channelId: String, channelName: String) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val notificationChannel = NotificationChannel(
            channelId,
            channelName,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            enableLights(true)
            lightColor = Color.RED
            enableVibration(true)
            description = "Firebase Network Update"
        }
        createNotificationChannel(notificationChannel)
    }
}

