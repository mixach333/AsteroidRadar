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

    init {
        viewModelScope.launch {
            val imageOfTheDay = refreshAsteroidsRepository.getImageOfTheDayUrl()
            if (imageOfTheDay.mediaType == "image") _imageUrl.value = imageOfTheDay
            refreshAsteroidsRepository.refreshAsteroids()
        }
    }

    private val _asteroidsList = refreshAsteroidsRepository.asteroids
    val asteroidList: LiveData<List<Asteroid>>
        get() = _asteroidsList

    private val _imageUrl = MutableLiveData<ImageOfTheDay>()
    val imageUrl: LiveData<ImageOfTheDay>
        get() = _imageUrl


}