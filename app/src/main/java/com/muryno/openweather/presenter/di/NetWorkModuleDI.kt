package com.muryno.openweather.presenter.di

import android.content.Context
import com.muryno.openweather.BuildConfig
import com.muryno.openweather.data.api.WeatherService
import com.muryno.openweather.data.util.NetworkConnectionInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetWorkModuleDI() {

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .build()
    }

    @Singleton
    @Provides
    fun provideWWService(retrofit: Retrofit): WeatherService {
        return retrofit.create(WeatherService::class.java)
    }

    @Singleton
    @Provides
    fun providesOkHttpClient(@ApplicationContext context: Context): OkHttpClient {
        val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(3, TimeUnit.MINUTES)
            .addInterceptor { chain ->
                val newRequest = chain.request().newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .build()
                chain.proceed(newRequest)
            }.addInterceptor(interceptor)
            .addInterceptor(NetworkConnectionInterceptor(context))
            .build()
    }

}