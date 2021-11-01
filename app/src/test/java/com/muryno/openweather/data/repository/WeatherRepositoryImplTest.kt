package com.muryno.openweather.data.repository

import com.google.common.truth.Truth
import com.muryno.openweather.data.model.CurrentWeather
import com.muryno.openweather.data.repository.datasource.WeatherRemoteDataSource
import com.muryno.openweather.data.util.Resource
import com.muryno.openweather.fakeData.Constants.location
import com.muryno.openweather.fakeData.FakeComingDaysWeatherData
import com.muryno.openweather.fakeData.FakeCurrentWeatherData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class WeatherRepositoryImplTest {

    private lateinit var weatherRepositoryImpl: WeatherRepositoryImpl
    private lateinit var weatherRemoteDataSource: WeatherRemoteDataSource
    private var weatherItem = FakeComingDaysWeatherData.comingDaysWeather
    private var currentWeatherItem = FakeCurrentWeatherData.currentWeather


    @Before
    fun setUp() {
        weatherRemoteDataSource = Mockito.mock(WeatherRemoteDataSource::class.java)
        weatherRepositoryImpl = WeatherRepositoryImpl(weatherRemoteDataSource = weatherRemoteDataSource )
    }


    @Test
    fun execute_current_weather_return_success_data() = runBlocking {
        Mockito.`when`(weatherRepositoryImpl.getWeatherFromAPI(location = location)).thenReturn(
            flow {
                emit(Resource.Success(currentWeatherItem))
            }
        )
        val reponse: Flow<Resource<CurrentWeather>> =
            weatherRepositoryImpl.getWeatherFromAPI(location = location)
        Truth.assertThat(reponse.collect { it is Resource.Success  }).isEqualTo( true)
    }
}