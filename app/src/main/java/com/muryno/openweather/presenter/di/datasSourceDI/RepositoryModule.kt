package com.muryno.openweather.presenter.di.datasSourceDI

import com.muryno.openweather.data.repository.WeatherRepositoryImpl
import com.muryno.openweather.data.repository.datasource.WeatherRemoteDataSource
import com.muryno.openweather.domain.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun providerRepository(
        WeatherRemoteDataSource: WeatherRemoteDataSource,
    ): WeatherRepository {
        return WeatherRepositoryImpl(
            weatherRemoteDataSource = WeatherRemoteDataSource,
        )
    }
}