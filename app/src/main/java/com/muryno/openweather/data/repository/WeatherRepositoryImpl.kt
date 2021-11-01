package com.muryno.openweather.data.repository


import android.util.Log
import com.muryno.openweather.data.model.ComingDaysWeather
import com.muryno.openweather.data.model.CurrentWeather
import com.muryno.openweather.data.repository.datasource.WeatherRemoteDataSource
import com.muryno.openweather.data.util.Resource
import com.muryno.openweather.domain.repository.WeatherRepository
import com.muryno.openweather.utils.ResponseToResourceUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

//
class WeatherRepositoryImpl(
    private val weatherRemoteDataSource: WeatherRemoteDataSource,
) : WeatherRepository {
    override suspend fun fetchCurrentFromApi(location: String): Flow< Resource<CurrentWeather>> =
        getWeatherFromAPI(location)

    override suspend fun fetchComingDaysWeatherFromApi(location: String): Flow<Resource<ComingDaysWeather>> =
        getComingDaysWeatherFromAPI(location)


    /*** stream Weather list from Weather  api ***/
     suspend fun getWeatherFromAPI(location: String):  Flow<Resource<CurrentWeather>> {
        return  flow {
            try {
                val response = weatherRemoteDataSource.getCurrentWeatherFromApi(location)
                val data = ResponseToResourceUtils<CurrentWeather>().responseToResource(response)
                emit(data)
            } catch (exception: Throwable) {
                Log.i("MyTag", exception.message.toString())
                  val errorResponse =    ResponseToResourceUtils<CurrentWeather>().responseToResource(
                      null, Throwable(
                          message =
                          exception.message.toString()
                      )
                  )
                  emit(errorResponse)
            }
        }
    }


    /*** Fetch Weather list from Weather  api ***/
    private suspend fun getComingDaysWeatherFromAPI(location: String): Flow<Resource<ComingDaysWeather>>{
        return flow {
            try {
                val response = weatherRemoteDataSource.getNextComingDaysWeatherFromApi(location)
                val data = ResponseToResourceUtils<ComingDaysWeather>().responseToResource(response)
                emit(data)
            } catch (exception: Throwable) {
                Log.i("MyTag", exception.message.toString())
                val errorResponse = ResponseToResourceUtils<ComingDaysWeather>().responseToResource(
                    null, Throwable(
                        message =
                        exception.message.toString()
                    )
                )
              emit(errorResponse)
            }
        }
    }


}