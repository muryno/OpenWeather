package com.muryno.openweather.data.util

import android.content.Context
import android.net.ConnectivityManager
import okhttp3.Interceptor
import okhttp3.Response

class NetworkConnectionInterceptor(context: Context) : Interceptor {

    private val applicationContext: Context = context.applicationContext;

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isInternetAvailable()) throw NoInternetException("Make sure you have an active data connection")

        return chain.proceed(chain.request())

    }

    private fun isInternetAvailable(): Boolean {
        val connectivityManager =
            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected;
    }
}