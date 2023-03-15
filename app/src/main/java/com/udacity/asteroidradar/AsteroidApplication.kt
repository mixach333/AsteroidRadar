package com.udacity.asteroidradar

import android.app.Application
import android.app.NotificationManager
import android.os.Build
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import androidx.work.*
import com.google.firebase.messaging.FirebaseMessaging
import com.udacity.asteroidradar.core.notifications.createFirebaseUpdateChannel
import com.udacity.asteroidradar.model.RefreshDataWork
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class AsteroidApplication : Application() {

    private val applicationScope = CoroutineScope(Dispatchers.Default)

    private fun delayedInit() {
        applicationScope.launch {
            setupRecurringWork()
        }
    }

    private fun setupRecurringWork() {
        val constraints = Constraints.Builder()
            .apply{
            setRequiredNetworkType(NetworkType.UNMETERED)
            setRequiresBatteryNotLow(true)
            setRequiresCharging(true)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                setRequiresDeviceIdle(true)
            }
        }
            .build()

        val repeatingRequest
                = PeriodicWorkRequestBuilder<RefreshDataWork>(1, TimeUnit.DAYS)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance().enqueueUniquePeriodicWork(
            RefreshDataWork.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingRequest)
    }

    private fun setupFirebaseNotifications(){
        val notificationManager =
            ContextCompat.getSystemService(
                applicationContext,
                NotificationManager::class.java
            ) as NotificationManager
        notificationManager.createFirebaseUpdateChannel(
            applicationContext.getString(R.string.update_notification_channel_id),
            applicationContext.getString(R.string.notification_channel_asteroids_update_name)
        )
        FirebaseMessaging.getInstance().subscribeToTopic("Asteroids_updates")
    }

    override fun onCreate() {
        super.onCreate()
        delayedInit()
        setupFirebaseNotifications()
    }
}