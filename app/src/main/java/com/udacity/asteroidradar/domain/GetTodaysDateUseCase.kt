package com.udacity.asteroidradar.domain

import com.udacity.asteroidradar.core.Constants
import java.text.SimpleDateFormat
import java.util.*


interface GetTodaysDateUseCase{
    fun getTodaysDate() : String
}

class GetTodaysDateUseCaseImpl : GetTodaysDateUseCase {
    override fun getTodaysDate() : String {
        val cal = Calendar.getInstance()
        val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
        return dateFormat.format(cal.time)
    }
}