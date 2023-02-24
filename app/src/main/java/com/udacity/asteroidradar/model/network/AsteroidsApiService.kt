package com.udacity.asteroidradar.model.network

import androidx.lifecycle.LiveData
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.core.ApiKey
import com.udacity.asteroidradar.core.Constants
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    /**
     * You have to create the ApiKey kotlin file inside the core package,
     * and define the following:
     *
     * object ApiKey {
     *            const val API_KEY = "here should be your own api key code"
     *            }
     *
     * You can get your own api key by signing up at https://api.nasa.gov/,
     * or if you want to test the app, you can just replace the last parameter
     * inside the getAsteroids() method as @Query("api_key") apiKey: String = "DEMO_KEY"
     * and for getImageOfTheDay() also
     */

    @GET("/neo/rest/v1/feed")
    suspend fun getAsteroids(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("api_key") apiKey: String = ApiKey.API_KEY
    ): String

    @GET("/planetary/apod")
    suspend fun getImageOfTheDay(
        @Query("api_key") apiKey: String = ApiKey.API_KEY
    ) : ImageOfTheDay
}

private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

object Network {
    private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    val retrofitService : ApiService by lazy { retrofit.create(ApiService::class.java)}
}