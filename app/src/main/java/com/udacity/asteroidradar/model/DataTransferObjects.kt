package com.udacity.asteroidradar.model

import androidx.lifecycle.Transformations.map
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.model.database.AsteroidDatabaseEntity
import com.udacity.asteroidradar.model.database.ImageOfTheDayEntity
import com.udacity.asteroidradar.model.network.ImageOfTheDay

fun List<Asteroid>.asDatabaseModel(): List<AsteroidDatabaseEntity> {
    return map {
        AsteroidDatabaseEntity(
            id = it.id,
            codename = it.codename,
            closeApproachDate = it.closeApproachDate,
            absoluteMagnitude = it.absoluteMagnitude,
            estimatedDiameter = it.estimatedDiameter,
            relativeVelocity = it.relativeVelocity,
            distanceFromEarth = it.distanceFromEarth,
            isPotentiallyHazardous = it.isPotentiallyHazardous
        )
    }
}

fun List<AsteroidDatabaseEntity>.asDomainModel(): List<Asteroid> {
    return map {
        Asteroid(
            id = it.id,
            codename = it.codename,
            closeApproachDate = it.closeApproachDate,
            absoluteMagnitude = it.absoluteMagnitude,
            estimatedDiameter = it.estimatedDiameter,
            relativeVelocity = it.relativeVelocity,
            distanceFromEarth = it.distanceFromEarth,
            isPotentiallyHazardous = it.isPotentiallyHazardous
        )
    }
}

fun ImageOfTheDayEntity.asDomainModel(): ImageOfTheDay = ImageOfTheDay(url, mediaType, title)

fun ImageOfTheDay.asDatabaseModel(): ImageOfTheDayEntity = ImageOfTheDayEntity(0L, url, mediaType, title)