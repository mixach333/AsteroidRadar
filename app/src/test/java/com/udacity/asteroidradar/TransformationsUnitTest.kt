package com.udacity.asteroidradar

import org.junit.Assert.assertEquals
import org.junit.Test
import com.udacity.asteroidradar.model.database.AsteroidDatabaseEntity
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.model.asDatabaseModel
import com.udacity.asteroidradar.model.asDomainModel
import com.udacity.asteroidradar.model.database.ImageOfTheDayEntity
import com.udacity.asteroidradar.model.network.ImageOfTheDay

class TransformationsUnitTest {
    private val databaseEntity1 = AsteroidDatabaseEntity(
        id = 0,
        codename = "Test codename1",
        closeApproachDate = "2023-03-15",
        absoluteMagnitude = 11.11,
        estimatedDiameter = 22.22,
        relativeVelocity = 33.33,
        distanceFromEarth = 44.44,
        isPotentiallyHazardous = false,
        isSaved = true
    )

    private val databaseEntity2 = AsteroidDatabaseEntity(
        id = 0,
        codename = "Test codename2",
        closeApproachDate = "2023-03-15",
        absoluteMagnitude = 11.11,
        estimatedDiameter = 22.22,
        relativeVelocity = 33.33,
        distanceFromEarth = 44.44,
        isPotentiallyHazardous = false,
        isSaved = true
    )

    private val domainEntity1 = Asteroid(
        id = 0,
        codename = "Test codename1",
        closeApproachDate = "2023-03-15",
        absoluteMagnitude = 11.11,
        estimatedDiameter = 22.22,
        relativeVelocity = 33.33,
        distanceFromEarth = 44.44,
        isPotentiallyHazardous = false,
        isSaved = true
    )
    private val domainEntity2 = Asteroid(
        id = 0,
        codename = "Test codename2",
        closeApproachDate = "2023-03-15",
        absoluteMagnitude = 11.11,
        estimatedDiameter = 22.22,
        relativeVelocity = 33.33,
        distanceFromEarth = 44.44,
        isPotentiallyHazardous = false,
        isSaved = true
    )

    private val domainEntitiesList = listOf(domainEntity1, domainEntity2)
    private val databaseEntitiesList = listOf(databaseEntity1, databaseEntity2)

    private val domainImageOfTheDay = ImageOfTheDay(
        url = "testUrlString",
        mediaType = "image",
        title = "testDescription"

    )
    private val databaseImageOfTheDay = ImageOfTheDayEntity(
        id = 0L,
        url = "testUrlString",
        mediaType = "image",
        title = "testDescription"
    )

    @Test
    fun domainToDatabaseAsteroid_isCorrect() {
        val domainToDatabaseEntity = domainEntity1.asDatabaseModel()
        assertEquals(domainToDatabaseEntity, databaseEntity1)
    }

    @Test
    fun databaseAsteroidsListToDomainAsteroidList_isCorrect() {
        val databaseListToDomainList = databaseEntitiesList.asDomainModel()
        assertEquals(databaseListToDomainList, domainEntitiesList)
    }

    @Test
    fun domainAsteroidsListToDatabaseAsteroidsList_isCorrect() {
        val domainListToDatabaseList = domainEntitiesList.asDatabaseModel()
        assertEquals(domainListToDatabaseList, databaseEntitiesList)
    }

    @Test
    fun domainImageToDatabaseEntityImage_isCorrect() {
        val domainImageToDatabaseEntityImage = domainImageOfTheDay.asDatabaseModel()
        assertEquals(domainImageToDatabaseEntityImage, databaseImageOfTheDay)
    }

    @Test
    fun databaseEntityImageToDomainImage_isCorrect() {
        val databaseEntityToDomainEntity = databaseImageOfTheDay.asDomainModel()
        assertEquals(databaseEntityToDomainEntity, domainImageOfTheDay)
    }
}