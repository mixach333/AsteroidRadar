package com.udacity.asteroidradar.model.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "image_database")
data class ImageOfTheDayEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val url: String,
    @ColumnInfo(name = "media_type")
    val mediaType: String,
    val title: String
)