package com.udacity.asteroidradar

import com.udacity.asteroidradar.core.Constants
import com.udacity.asteroidradar.domain.usecases.GetSevenDateFromNowUseCase
import com.udacity.asteroidradar.domain.usecases.GetTodaysDateUseCase
import org.junit.Assert.assertEquals
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*


class DaysUnitTest {
    @Test
    fun today_isCorrect() {
        val cal = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val today = dateFormat.format(cal.time)
        assertEquals(GetTodaysDateUseCase().getDate(), today)
    }

    @Test
    fun sevenDaysFromNow_isCorrect() {
        val cal = Calendar.getInstance().also { it.roll(Calendar.DAY_OF_YEAR, 6) }
        val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
        assertEquals(dateFormat.format(cal.time), GetSevenDateFromNowUseCase().getDate())
    }
}

