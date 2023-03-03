package com.udacity.asteroidradar.domain.usecases

import com.udacity.asteroidradar.core.Constants
import java.text.SimpleDateFormat
import java.util.*

class GetTodaysDateUseCase :  GetDateUseCase{
    override fun getDate() : String {
        val cal = Calendar.getInstance()
        val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
        return dateFormat.format(cal.time)
    }
}