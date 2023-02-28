package com.udacity.asteroidradar.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [AsteroidDatabaseEntity::class, ImageOfTheDayEntity::class],
    version = 4,
    exportSchema = false
)
abstract class AsteroidDatabase : RoomDatabase() {
    abstract val asteroidsDao: AsteroidsDao
    abstract val imageDao: ImageDao

    companion object {
        @Volatile
        var INSTANCE: AsteroidDatabase? = null

        fun getDatabase(context: Context): AsteroidDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    AsteroidDatabase::class.java,
                    "asteroid_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}