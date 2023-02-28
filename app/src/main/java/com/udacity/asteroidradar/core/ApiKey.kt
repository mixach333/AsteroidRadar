package com.udacity.asteroidradar.core

import com.udacity.asteroidradar.BuildConfig

object ApiKey {
     val API_KEY = BuildConfig.API_KEY.ifEmpty {
         "DEMO_KEY"
     }// Change to your Api key or replace with "DEMO_KEY" value
}