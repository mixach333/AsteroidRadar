package com.udacity.asteroidradar.domain

import com.udacity.asteroidradar.core.Constants
import java.text.SimpleDateFormat
import java.util.*

interface GetSevenDateFromNowUseCase {
    fun getSevenDateFromNow() : String
}

class GetSevenDateFromNowUseCaseImpl : GetSevenDateFromNowUseCase{

    override fun getSevenDateFromNow(): String {
        val cal = Calendar.getInstance()
        val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
        cal.roll(Calendar.DAY_OF_YEAR, Constants.DEFAULT_END_DATE_DAYS - 1)
        return dateFormat.format(cal.time)
    }

}