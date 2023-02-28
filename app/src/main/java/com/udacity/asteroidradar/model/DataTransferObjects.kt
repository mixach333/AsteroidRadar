package com.udacity.asteroidradar.model

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
            isPotentiallyHazardous = it.isPotentiallyHazardous,
            isSaved = it.isSaved
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
            isPotentiallyHazardous = it.isPotentiallyHazardous,
            isSaved = it.isSaved
        )
    }
}

fun Asteroid.asDatabaseModel() : AsteroidDatabaseEntity{
    return AsteroidDatabaseEntity(
        id = id,
        codename = codename,
        closeApproachDate = closeApproachDate,
        absoluteMagnitude = absoluteMagnitude,
        estimatedDiameter = estimatedDiameter,
        relativeVelocity = relativeVelocity,
        distanceFromEarth = distanceFromEarth,
        isPotentiallyHazardous = isPotentiallyHazardous,
        isSaved = isSaved
    )
}

fun ImageOfTheDayEntity.asDomainModel(): ImageOfTheDay = ImageOfTheDay(url, mediaType, title)

fun ImageOfTheDay.asDatabaseModel(): ImageOfTheDayEntity = ImageOfTheDayEntity(0L, url, mediaType, title)