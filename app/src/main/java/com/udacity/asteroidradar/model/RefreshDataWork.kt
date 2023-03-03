package com.udacity.asteroidradar.model

import android.app.Application
import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.domain.usecases.GetSevenDateFromNowUseCase
import com.udacity.asteroidradar.domain.usecases.GetTodaysDateUseCase

class RefreshDataWork(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }

    override suspend fun doWork(): Result {
        val repository = RefreshAsteroidsRepository(
            applicationContext as Application,
            GetTodaysDateUseCase().getDate(),
            GetSevenDateFromNowUseCase().getDate()
        )
        return try {
            repository.refreshAsteroids()
            repository.clearAsteroidBeforeDateFromDatabase(GetTodaysDateUseCase().getDate())
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }

}