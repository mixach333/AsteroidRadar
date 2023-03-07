package com.udacity.asteroidradar.model

import android.app.Application
import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.domain.usecases.GetSevenDateFromNowUseCase
import com.udacity.asteroidradar.domain.usecases.GetTodaysDateUseCase
import com.udacity.asteroidradar.presetnation.notifications.createAsteroidsUpdateChannel
import com.udacity.asteroidradar.presetnation.notifications.sendNotification

class RefreshDataWork(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }

    override suspend fun doWork(): Result {
        Log.d("WorkerTag", "The Worker started")
        val repository = RefreshAsteroidsRepository(
            applicationContext as Application,
            GetTodaysDateUseCase().getDate(),
            GetSevenDateFromNowUseCase().getDate()
        )
        val notificationManager =
            ContextCompat.getSystemService(
                applicationContext,
                NotificationManager::class.java
            ) as NotificationManager
        notificationManager.createAsteroidsUpdateChannel(
            applicationContext.getString(R.string.notification_channel_asteroids_update_id),
            applicationContext.getString(R.string.notification_channel_asteroids_update_name)
        )
        return try {
            Log.d("WorkerTag", "Success")
            repository.refreshAsteroids()
            repository.clearAsteroidBeforeDateFromDatabase(GetTodaysDateUseCase().getDate())
            notificationManager.sendNotification(
                applicationContext.getString(R.string.asteroids_notification_message_success),
                applicationContext
            )
            Result.success()
        } catch (e: Exception) {
            Log.d("WorkerTag", "False")
            notificationManager.sendNotification(
                applicationContext.getString(R.string.asteroids_notification_message_failure),
                applicationContext
            )
            Result.retry()
        }
    }

}