package com.muryno.openweather.data.repository.datasourceImpl

import com.muryno.openweather.data.api.WeatherService
import com.muryno.openweather.data.model.ComingDaysWeather
import com.muryno.openweather.data.model.CurrentWeather

import com.muryno.openweather.data.repository.datasource.WeatherRemoteDataSource
import retrofit2.Response
import javax.inject.Inject

class WeatherRemoteDataSourceImpl @Inject constructor(
    private val WeatherService: WeatherService,
) : WeatherRemoteDataSource {
    override suspend fun getCurrentWeatherFromApi(location: String): Response<CurrentWeather> =
        WeatherService.getWeatherByQuery(location)

    override suspend fun getNextComingDaysWeatherFromApi(location: String): Response<ComingDaysWeather> =
        WeatherService.getSubsequenceWeatherByLocation(location)
}