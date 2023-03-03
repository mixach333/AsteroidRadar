package com.udacity.asteroidradar.domain.usecases

import com.udacity.asteroidradar.core.Constants
import java.text.SimpleDateFormat
import java.util.*

class GetSevenDateFromNowUseCase : GetDateUseCase{

    override fun getDate(): String {
        val cal = Calendar.getInstance()
        val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
        cal.roll(Calendar.DAY_OF_YEAR, Constants.DEFAULT_END_DATE_DAYS - 1)
        return dateFormat.format(cal.time)
    }

}