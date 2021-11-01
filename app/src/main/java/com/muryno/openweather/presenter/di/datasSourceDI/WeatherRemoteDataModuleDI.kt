package com.muryno.openweather.presenter.di.datasSourceDI

import com.muryno.openweather.data.api.WeatherService
import com.muryno.openweather.data.repository.datasource.WeatherRemoteDataSource
import com.muryno.openweather.data.repository.datasourceImpl.WeatherRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class WeatherRemoteDataModuleDI {

    @Singleton
    @Provides
    fun providersRemoteDataSource(wwDbServiceCarbon: WeatherService): WeatherRemoteDataSource {
        return WeatherRemoteDataSourceImpl(
            wwDbServiceCarbon
        )
    }
}