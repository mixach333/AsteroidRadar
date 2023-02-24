package com.udacity.asteroidradar.model.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AsteroidsDao {

        @Insert
        suspend fun insertAll(asteroids: List<AsteroidDatabaseEntity>)

        @Query("select * from asteroid_database")
        fun getAllAsteroids() : LiveData<List<AsteroidDatabaseEntity>>
}