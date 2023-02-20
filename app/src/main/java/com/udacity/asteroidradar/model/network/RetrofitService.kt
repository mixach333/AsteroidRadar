package com.udacity.asteroidradar.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.core.Constants
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

interface ApiService {
    @GET
    suspend fun getAsteroids(): Deferred<List<Asteroid>>
}

private val moshi = Moshi.Builder().add(ScalarsConverterFactory.create()).build()

object Network {
    private val retrofit = Retrofit.Builder().baseUrl(Constants.BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    val retrofitService = retrofit.create(ApiService::class.java)
}