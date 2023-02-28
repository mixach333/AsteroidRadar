package com.udacity.asteroidradar.model

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.model.database.AsteroidDatabase
import com.udacity.asteroidradar.model.database.AsteroidDatabaseEntity
import com.udacity.asteroidradar.model.network.ImageOfTheDay
import com.udacity.asteroidradar.model.network.Network
import com.udacity.asteroidradar.model.network.parseAsteroidsJsonResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.HttpException
import java.io.*


class RefreshAsteroidsRepository(
    application: Application,
    private val today: String,
    private val sevenDayFromNow: String
) {

    private val database = AsteroidDatabase.getDatabase(application)

    val todayAsteroids: LiveData<List<Asteroid>> =
        Transformations.map(database.asteroidsDao.getAsteroids(today, today)) {
            Log.d(
                "RefreshAsteroidsRepo", "Size for today asteroids retrieved from db is: ${it.size}"
            )
            it.asDomainModel()
        }
    val weekAsteroids: LiveData<List<Asteroid>> =
        Transformations.map(database.asteroidsDao.getAsteroids(today, sevenDayFromNow)) {
            it.asDomainModel()
        }
    val savedAsteroids: LiveData<List<Asteroid>> =
        Transformations.map(database.asteroidsDao.getSavedAsteroids()) {
            it.asDomainModel()
        }

    val imageOfTheDay: LiveData<ImageOfTheDay> =
        Transformations.map(database.imageDao.getImageOfTheDay()) {
            it.asDomainModel()
        }

    private suspend fun fetchAsteroids(): List<Asteroid> {
        val result: List<Asteroid>
        try {
            val jsonString = Network.retrofitService.getAsteroids(
                today,
                sevenDayFromNow
            )
            result = parseAsteroidsJsonResult(JSONObject(jsonString))
            Log.d("RefreshAsteroidsRepo", "Asteroids Fetch successful: $result")
            return result
        } catch (e: IOException) {
            Log.e(
                "RefreshAsteroidsRepo",
                "IOException occurred for asteroids fetch: ${e.localizedMessage}"
            )
        } catch (e: HttpException) {
            Log.e(
                "RefreshAsteroidsRepo",
                "HttpException occurred for asteroids fetch: ${e.localizedMessage}"
            )
        } catch (e: Throwable) {
            Log.e(
                "RefreshAsteroidsRepo",
                "Exception occurred for asteroids fetch: ${e.localizedMessage}"
            )
        }
        return emptyList()
    }

    private suspend fun getImageOfTheDayUrl(): ImageOfTheDay? {
        try {
            val result = Network.retrofitService.getImageOfTheDay()
            if (result.mediaType != "image") return null
            Log.d("RefreshAsteroidsRepo", "ImageOfTheDay Fetch successful: $result")
            return result
        } catch (e: IOException) {
            Log.e(
                "RefreshAsteroidsRepo",
                "ImageOfTheDay fetch IOException occurred: ${e.localizedMessage}"
            )
        } catch (e: HttpException) {
            Log.e(
                "RefreshAsteroidsRepo",
                "ImageOfTheDay fetch HttpException occurred: ${e.localizedMessage}"
            )
        } catch (e: Throwable) {
            Log.e(
                "RefreshAsteroidsRepo",
                "ImageOfTheDay fetch Exception occurred: ${e.localizedMessage}"
            )
        }
        return null
    }

    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO) {
            database.asteroidsDao.insertAll(fetchAsteroids().asDatabaseModel())
        }
    }

    suspend fun refreshImageOfTHeDay() {
        withContext(Dispatchers.IO) {
            getImageOfTheDayUrl()?.let {
                if (it.mediaType == "image")
                    database.imageDao.insertImage(it.asDatabaseModel())
            }
        }
    }

    suspend fun updateAsteroidInDatabase(asteroidDatabaseEntity: AsteroidDatabaseEntity) {
        withContext(Dispatchers.IO) {
            database.asteroidsDao.update(asteroidDatabaseEntity)
        }
    }

    suspend fun clearAsteroidBeforeDateFromDatabase(beforeDate: String) {
        withContext(Dispatchers.IO) {
            database.asteroidsDao.deleteOldAsteroids(beforeDate)
        }

    }
}
