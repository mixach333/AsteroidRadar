package com.udacity.asteroidradar.presetnation

import android.app.Application
import androidx.lifecycle.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessaging
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.domain.usecases.GetSevenDateFromNowUseCase
import com.udacity.asteroidradar.domain.usecases.GetTodaysDateUseCase
import com.udacity.asteroidradar.model.RefreshAsteroidsRepository
import com.udacity.asteroidradar.model.asDatabaseModel
import com.udacity.asteroidradar.model.database.DateFilter
import com.udacity.asteroidradar.model.network.ImageOfTheDay
import com.udacity.asteroidradar.presetnation.login.AuthenticationState
import com.udacity.asteroidradar.presetnation.login.FirebaseUserLiveData
import kotlinx.coroutines.launch

class MainViewModel(private val application: Application) : AndroidViewModel(application) {
    val authenticationState = FirebaseUserLiveData().map { user ->
        if (user != null) {
            AuthenticationState.AUTHENTICATED
        } else {
            AuthenticationState.UNAUTHENTICATED
        }
    }

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

    fun updateAsteroidInDatabase(asteroid: Asteroid) {
        viewModelScope.launch {
            refreshAsteroidsRepository.updateAsteroidInDatabase(asteroid.asDatabaseModel())
        }
    }

    fun subscribeToAsteroidsTopic() {
        FirebaseMessaging.getInstance().subscribeToTopic("Asteroids_updates")
    }

    fun getCurrentUserName(): String {
        val userName = FirebaseAuth.getInstance().currentUser?.displayName?:"Guest"
        return application.getString(R.string.main_screen_user_name, userName)
    }

}