package com.udacity.asteroidradar.model.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AsteroidsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(asteroids: List<AsteroidDatabaseEntity>)

    @Query("select * from asteroid_database WHERE close_approach_date>=:startDateFilter AND" +
            " close_approach_date <=:endDateFilter ORDER BY close_approach_date ASC")
    fun getAsteroids(
        startDateFilter: String,
        endDateFilter: String
    ): LiveData<List<AsteroidDatabaseEntity>>

    @Query("SELECT * FROM asteroid_database WHERE is_saved = 1 ORDER BY close_approach_date ASC")
    fun getSavedAsteroids(): LiveData<List<AsteroidDatabaseEntity>>

    @Query("DELETE FROM asteroid_database WHERE close_approach_date<:oldDate")
    suspend fun deleteOldAsteroids(oldDate: String)

    @Update
    suspend fun update(asteroidDatabaseEntity: AsteroidDatabaseEntity)
}