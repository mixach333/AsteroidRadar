package com.udacity.asteroidradar.presatnation

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.model.RefreshAsteroidsRepository
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val refreshAsteroidsRepository = RefreshAsteroidsRepository(application)

    init {
        getRemoteAsteroids()
    }
    private val _asteroidsList = MutableLiveData<List<Asteroid>>()
    val asteroidList: LiveData<List<Asteroid>>
        get() = _asteroidsList

    fun getRemoteAsteroids() {
        viewModelScope.launch {
            val remoteAsteroids = refreshAsteroidsRepository.fetchAsteroids()
            if(remoteAsteroids.isNotEmpty()){
                _asteroidsList.value = remoteAsteroids
                Log.d("ViewModel fetch", "Fetch successful + \n ${_asteroidsList.value.toString()}")
            } else {
                Log.d("ViewModel fetch", "Fetch unsuccessful and the result is $remoteAsteroids")
            }
        }
    }

}