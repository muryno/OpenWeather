package com.muryno.openweather.data.api

import com.muryno.openweather.BuildConfig
import com.muryno.openweather.data.model.ComingDaysWeather
import com.muryno.openweather.data.model.CurrentWeather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("weather")
    suspend fun getWeatherByQuery(
        @Query("q") q: String?, @Query("appid") apiKey: String
        = BuildConfig.API_KEY
    ):
            Response<CurrentWeather>

    @GET("forecast")
    suspend fun getSubsequenceWeatherByLocation(
        @Query("q") q: String?,
        @Query("appid") apiKey: String
        = BuildConfig.API_KEY
    ):
            Response<ComingDaysWeather>

}


