package com.muryno.openweather.data.repository.datasource

import com.muryno.openweather.data.model.ComingDaysWeather
import com.muryno.openweather.data.model.CurrentWeather
import retrofit2.Response

interface WeatherRemoteDataSource {
    suspend fun getCurrentWeatherFromApi(location: String): Response<CurrentWeather>
    suspend fun getNextComingDaysWeatherFromApi(location: String): Response<ComingDaysWeather>
}