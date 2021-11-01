package com.muryno.openweather.utils

import com.muryno.openweather.data.util.Resource
import retrofit2.Response

open class ResponseToResourceUtils<T> {
    fun responseToResource(
        response: Response<T>?,
        exception: Throwable = Throwable(message = "please try again")
    ): Resource<T> {
        if (response != null && response.isSuccessful) {

            response.body()?.let { result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(exception)

    }
}