package com.muryno.openweather.data.api
import com.google.common.truth.Truth.assertThat
import com.muryno.openweather.core.BaseUnitTest
import com.muryno.openweather.fakeData.Constants.location
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@HiltAndroidTest
@ExperimentalCoroutinesApi
class WeatherServiceTest: BaseUnitTest() {

    lateinit var weatherService: WeatherService
    private lateinit var mockWebServer: MockWebServer
    @Before
    fun setsUp() {
        mockWebServer = MockWebServer()
        weatherService = Retrofit.Builder().baseUrl(mockWebServer.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(WeatherService::class.java)
    }

    fun enqueueMockUpResponse(fileName: String) {
        val inputStream = javaClass.classLoader?.getResourceAsStream(fileName)
        val source = inputStream?.source()?.buffer()

        val mockResponse = MockResponse()
        mockResponse.setBody(source!!.readString(Charsets.UTF_8))
        mockWebServer.enqueue(mockResponse)
    }


    @Test
    fun test_Current_Weather_Response_Has_Data() {

        runBlocking {
            enqueueMockUpResponse("current_weather_response.json")
            val response = weatherService.getWeatherByQuery(location).body()
            val predefineResponse = mockWebServer.takeRequest()
            assertThat(response).isNotNull()
            assertThat(predefineResponse.path).isEqualTo("/weather?q=us&appid=d0308696713bb16f9990665820c29dcb")
        }
    }

    @Test
    fun test_Subsequence_Weather_By_Location_Response_Has_Data() {

        runBlocking {
            enqueueMockUpResponse("subsequence_weather_response.json")
            val response = weatherService.getSubsequenceWeatherByLocation(location).body()
            val predefineResponse = mockWebServer.takeRequest()
            assertThat(response).isNotNull()
            assertThat(predefineResponse.path).isEqualTo("/forecast?q=us&appid=d0308696713bb16f9990665820c29dcb")
        }
    }




    @Test
    fun test_Current_Weather_Response_return_data() {
        runBlocking {
            enqueueMockUpResponse("current_weather_response.json")
            val response = weatherService.getWeatherByQuery(location).body()
            assertThat(response).isNotNull()
            assertThat(response?.base).isEqualTo("stations")

        }
    }

    @Test
    fun test_Subsequence_Weather_By_Location_Response_return_data() {
        runBlocking {
            enqueueMockUpResponse("subsequence_weather_response.json")
            val response = weatherService.getSubsequenceWeatherByLocation(location).body()
            assertThat(response).isNotNull()
            assertThat(response?.cnt).isEqualTo(40)

        }
    }
}