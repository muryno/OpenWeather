package com.muryno.openweather.data.repository.datasource

import com.google.common.truth.Truth.assertThat
import com.muryno.openweather.data.model.ComingDaysWeather
import com.muryno.openweather.data.model.CurrentWeather
import com.muryno.openweather.fakeData.Constants.location
import com.muryno.openweather.fakeData.FakeComingDaysWeatherData
import com.muryno.openweather.fakeData.FakeCurrentWeatherData
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import retrofit2.Response

class WeatherRemoteDataSourceTest {

    private lateinit var weatherRemoteDataSource : WeatherRemoteDataSource
    private var weatherItem = FakeComingDaysWeatherData.comingDaysWeather
    private var currentWeatherItem = FakeCurrentWeatherData.currentWeather
    @Before
    fun setUp() {
        weatherRemoteDataSource = Mockito.mock(WeatherRemoteDataSource::class.java)
    }

    @Test
    fun execute_current_weather_return_success_data() = runBlocking {
        Mockito.`when`(weatherRemoteDataSource.getCurrentWeatherFromApi(location)).thenReturn(
            Response.success(currentWeatherItem)
        )
        val reponse: Response<CurrentWeather> =
            weatherRemoteDataSource.getCurrentWeatherFromApi(location)
        assertThat(reponse.body()).isEqualTo(currentWeatherItem)
    }


    @Test
    fun execute_subsequence_weather_return_success_data() = runBlocking {
        Mockito.`when`(weatherRemoteDataSource.getNextComingDaysWeatherFromApi(location)).thenReturn(
            Response.success(weatherItem)
        )
        val reponse: Response<ComingDaysWeather> =
            weatherRemoteDataSource.getNextComingDaysWeatherFromApi(location)
        assertThat(reponse.body()).isEqualTo(weatherItem)
    }


}