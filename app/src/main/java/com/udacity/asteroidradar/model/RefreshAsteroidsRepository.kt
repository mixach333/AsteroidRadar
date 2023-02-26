package com.udacity.asteroidradar.model

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.core.Constants
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.model.database.AsteroidDatabase
import com.udacity.asteroidradar.model.network.ImageOfTheDay
import com.udacity.asteroidradar.model.network.Network
import com.udacity.asteroidradar.model.network.parseAsteroidsJsonResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.HttpException
import java.io.*
import java.text.SimpleDateFormat
import java.util.*


class RefreshAsteroidsRepository(application: Application) {

    private val database = AsteroidDatabase.getDatabase(application)
    val asteroids: LiveData<List<Asteroid>> =
        Transformations.map(database.asteroidsDao.getAllAsteroids()) {
            it.asDomainModel()
        }
    val imageOfTheDay: LiveData<ImageOfTheDay> =
        Transformations.map(database.imageDao.getImageOfTheDay()) {
            it.asDomainModel()
        }


    suspend fun fetchAsteroids(): List<Asteroid> {
        val result: List<Asteroid>
        val sevenDaysRangeFromNow = getSevenDaysRangeFromNow()
        try {
            val jsonString = Network.retrofitService.getAsteroids(
                sevenDaysRangeFromNow[0],
                sevenDaysRangeFromNow[1]
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

    suspend fun getImageOfTheDayUrl(): ImageOfTheDay? {
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

    private fun getSevenDaysRangeFromNow(): List<String> {
        val cal = Calendar.getInstance()
        val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
        val formattedToday = dateFormat.format(cal.time)
        cal.roll(Calendar.DAY_OF_YEAR, Constants.DEFAULT_END_DATE_DAYS - 1)
        val formattedEndDay = dateFormat.format(cal.time)
        return listOf(formattedToday, formattedEndDay)
    }

}