package com.muryno.openweather

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.muryno.openweather.data.model.CurrentWeather
import com.muryno.openweather.databinding.ActivityMainBinding
import com.muryno.openweather.presenter.viewModel.WeatherViewModel
import com.muryno.openweather.utils.convertCelciusToFahrenheit
import com.muryno.openweather.utils.getArtResourceForWeatherCondition
import com.muryno.openweather.utils.getCurrentDate
import dagger.hilt.android.AndroidEntryPoint
import java.lang.String

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val weatherViewModel by viewModels<WeatherViewModel>()
    lateinit var  binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =   DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.apply {
            viewModel = weatherViewModel
            lifecycleOwner = this@MainActivity
            contentActivity.viewModel = weatherViewModel
            contentActivity.swipeRefresh.setOnRefreshListener {
                weatherViewModel.fetchWeather()
            }
        }

        weatherViewModel.currentWeathers.observe(this, {
            if (it!= null){
                views(binding=binding,currentWeatherData = it)

            }
        })

        weatherViewModel.loading.observe(this, {
            if (it==false){
                binding.contentActivity.swipeRefresh.isRefreshing = false
            }
        })


    }

    private fun views(binding: ActivityMainBinding, currentWeatherData : CurrentWeather?){
        binding.contentActivity.apply {
            currentDate.text = getCurrentDate()
            txtTemp.text = String.format(getString(R.string.weather_fahrenheit), convertCelciusToFahrenheit(currentWeatherData?.main?.temp ?: 0.0))
            imgIcon.setImageResource(getArtResourceForWeatherCondition(currentWeatherData?.weather?.get(0)?.id ?: 200))
            txtWind.text = currentWeatherData?.wind?.deg?.toString() ?: "0"
            txtPres.text = currentWeatherData?.main?.pressure?.toString()  ?: "0"
            txtHumidity.text = currentWeatherData?.main?.humidity?.toString()  ?: "0"

        }

    }
}