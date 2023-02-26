package com.udacity.asteroidradar.presetnation

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.model.RefreshAsteroidsRepository
import com.udacity.asteroidradar.model.network.ImageOfTheDay
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlin.system.measureTimeMillis

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val refreshAsteroidsRepository = RefreshAsteroidsRepository(application)
    private val _asteroidsList = refreshAsteroidsRepository.asteroids
    val asteroidList: LiveData<List<Asteroid>>
        get() = _asteroidsList

    private val _imageUrl = refreshAsteroidsRepository.imageOfTheDay
    val imageUrl: LiveData<ImageOfTheDay>
        get() = _imageUrl

    init {
        viewModelScope.launch {
            refreshAsteroidsRepository.refreshImageOfTHeDay()
//            refreshAsteroidsRepository.imageOfTheDay.value?.let{
//                if(it.mediaType=="image")
//            }
            refreshAsteroidsRepository.refreshAsteroids()
        }
    }




}