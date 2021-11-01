package com.muryno.openweather.utils

import com.google.common.truth.Truth
import com.muryno.openweather.R
import junit.framework.Assert.assertNotNull
import org.junit.Test



class UtilsTest {







    @Test
    fun getFormattedDateWithNullData(){
        val dateFormat = null
        Truth.assertThat(getFormattedDate(dateFormat,day)).isNotEmpty()

    }

    @Test
    fun getDayFromFormattedDateWitDateString(){
        val dateFormat = "2021-11-01 09:00:00"
        val  result = "Mon"
        Truth.assertThat(result).isEqualTo(getFormattedDate(dateFormat,day))
    }

    @Test
    fun getMonthFromFormattedDateWitDateString(){
        val dateFormat = "2021-11-01 09:00:00"
        val  result = "21 Nov"
        Truth.assertThat(result).isEqualTo(getFormattedDate(dateFormat, month))

    }

    @Test
    fun getTimeFromFormattedDateWitDateString(){
        val dateFormat = "2021-11-01 09:00:00"
        val  result = "09:00"   //mind the upper case incase you want to test with your date
        Truth.assertThat(result).isEqualTo(getFormattedDate(dateFormat, time))
    }

    @Test
    fun getCurrentDateNotNullData(){
        assertNotNull(getCurrentDate())
    }

    @Test
    fun getArtResourceForWeatherConditionByWeatherId() {
        val weather_id = 204
        Truth.assertThat(R.drawable.art_storm).isEqualTo(getArtResourceForWeatherCondition(weather_id))

    }

    @Test
    fun getArtResourceForWeatherConditionWithZeroNotNull() {
        val weather_id = 0
        assertNotNull( getArtResourceForWeatherCondition(weather_id))
    }
}
