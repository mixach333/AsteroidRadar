package com.udacity.asteroidradar.core.notifications

import android.app.NotificationManager
import android.util.Log
import androidx.core.content.ContextCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d(TAG, "From: ${remoteMessage.from}")

        // Checks if message contains a data payload.
        remoteMessage.data.let {
            Log.d(TAG, "Message data payload: " + remoteMessage.data)
        }

        // Checks if message contains a notification payload.
        remoteMessage.notification?.let {
            Log.d(TAG, "Message Notification Body: ${it.body}")
            sendNotification(it.body!!)
        }
    }


    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")
    }

    private fun sendNotification(messageBody: String) {
        val notificationManager = ContextCompat.getSystemService(
            applicationContext,
            NotificationManager::class.java
        ) as NotificationManager
        notificationManager.sendNotification(messageBody, applicationContext)
    }

    companion object {
        private const val TAG = "MyFirebaseMsgService"
    }
}