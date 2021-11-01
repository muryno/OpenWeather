package com.muryno.openweather.domain.repository

import com.muryno.openweather.data.model.ComingDaysWeather
import com.muryno.openweather.data.model.CurrentWeather
import com.muryno.openweather.data.util.Resource
import kotlinx.coroutines.flow.Flow


interface WeatherRepository {
    suspend fun fetchCurrentFromApi(location: String): Flow<Resource<CurrentWeather>>
    suspend fun fetchComingDaysWeatherFromApi(location: String): Flow<Resource<ComingDaysWeather>>
}