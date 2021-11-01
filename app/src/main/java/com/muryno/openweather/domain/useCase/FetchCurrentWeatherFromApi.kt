package com.muryno.openweather.domain.useCase

import com.muryno.openweather.data.model.CurrentWeather
import com.muryno.openweather.data.util.Resource
import com.muryno.openweather.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchCurrentWeatherFromApi @Inject constructor(private val weatherRepository: WeatherRepository) {
   suspend fun call(location : String): Flow<Resource<CurrentWeather>> = weatherRepository.fetchCurrentFromApi(location = location)
}