package com.udacity.asteroidradar.presetnation

import android.app.Application
import androidx.lifecycle.*
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.domain.usecases.GetSevenDateFromNowUseCase
import com.udacity.asteroidradar.domain.usecases.GetTodaysDateUseCase
import com.udacity.asteroidradar.model.RefreshAsteroidsRepository
import com.udacity.asteroidradar.model.asDatabaseModel
import com.udacity.asteroidradar.model.database.DateFilter
import com.udacity.asteroidradar.model.network.ImageOfTheDay
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val refreshAsteroidsRepository = RefreshAsteroidsRepository(
        application,
        GetTodaysDateUseCase().getDate(),
        GetSevenDateFromNowUseCase().getDate()
    )

    init {
        viewModelScope.launch {
            refreshAsteroidsRepository.refreshImageOfTHeDay()
            refreshAsteroidsRepository.refreshAsteroids()
        }
    }

    private val _asteroidsList = MutableLiveData<DateFilter>(DateFilter.WEEK)
    val asteroidList = Transformations.switchMap(_asteroidsList) { dateFilter ->
        when (dateFilter) {
            DateFilter.WEEK -> refreshAsteroidsRepository.weekAsteroids
            DateFilter.TODAY -> refreshAsteroidsRepository.todayAsteroids
            else -> refreshAsteroidsRepository.savedAsteroids
        }
    }

    private val _imageUrl = refreshAsteroidsRepository.imageOfTheDay
    val imageUrl: LiveData<ImageOfTheDay>
        get() = _imageUrl


    fun showFilteredAsteroids(filter: DateFilter) {
        _asteroidsList.postValue(filter)
    }

    fun updateAsteroidInDatabase(asteroid: Asteroid){
        viewModelScope.launch {
            refreshAsteroidsRepository.updateAsteroidInDatabase(asteroid.asDatabaseModel())
        }
    }


}