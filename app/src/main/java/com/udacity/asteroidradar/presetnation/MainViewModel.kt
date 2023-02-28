package com.udacity.asteroidradar.presetnation

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.udacity.asteroidradar.domain.*
import com.udacity.asteroidradar.model.RefreshAsteroidsRepository
import com.udacity.asteroidradar.model.asDomainModel
import com.udacity.asteroidradar.model.database.DateFilter
import com.udacity.asteroidradar.model.network.ImageOfTheDay
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlin.system.measureTimeMillis

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val getTodaysDateUseCase: GetTodaysDateUseCase = GetTodaysDateUseCaseImpl()
    private val getSevenDateFromNowUseCase: GetSevenDateFromNowUseCase =
        GetSevenDateFromNowUseCaseImpl()
    private val refreshAsteroidsRepository = RefreshAsteroidsRepository(
        application,
        getTodaysDateUseCase.getTodaysDate(),
        getSevenDateFromNowUseCase.getSevenDateFromNow()
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

//    val weekAsteroids = refreshAsteroidsRepository.weekAsteroids
//    val todayAsteroids = refreshAsteroidsRepository.todayAsteroids
//    val savedAsteroids = refreshAsteroidsRepository.savedAsteroids

    private val _imageUrl = refreshAsteroidsRepository.imageOfTheDay
    val imageUrl: LiveData<ImageOfTheDay>
        get() = _imageUrl


    fun showFilteredAsteroids(filter: DateFilter) {
        _asteroidsList.postValue(filter)
    }


}