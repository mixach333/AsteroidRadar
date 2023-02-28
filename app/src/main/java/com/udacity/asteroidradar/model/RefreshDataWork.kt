package com.udacity.asteroidradar.model

import android.app.Application
import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.domain.GetSevenDateFromNowUseCaseImpl
import com.udacity.asteroidradar.domain.GetTodaysDateUseCaseImpl

class RefreshDataWork(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }

    override suspend fun doWork(): Result {
        val repository = RefreshAsteroidsRepository(
            applicationContext as Application,
            GetTodaysDateUseCaseImpl().getTodaysDate(),
            GetSevenDateFromNowUseCaseImpl().getSevenDateFromNow()
        )
        return try {
            repository.refreshAsteroids()
            repository.clearAsteroidBeforeDateFromDatabase(GetTodaysDateUseCaseImpl().getTodaysDate())
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }

}