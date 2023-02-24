package com.udacity.asteroidradar.model.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AsteroidsDao {

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun insertAll(asteroids: List<AsteroidDatabaseEntity>)

        @Query("select * from asteroid_database ORDER BY close_approach_date ASC")
        fun getAllAsteroids() : LiveData<List<AsteroidDatabaseEntity>>
}