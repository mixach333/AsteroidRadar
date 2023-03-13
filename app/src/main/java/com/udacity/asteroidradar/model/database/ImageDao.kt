package com.udacity.asteroidradar.model.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ImageDao {

    @Query("select * from image_database ORDER BY id DESC LIMIT 1")
    fun getImageOfTheDay() : LiveData<ImageOfTheDayEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertImage(image: ImageOfTheDayEntity)

    @Query("DELETE FROM asteroid_database")
    suspend fun deleteImageUrls()
}