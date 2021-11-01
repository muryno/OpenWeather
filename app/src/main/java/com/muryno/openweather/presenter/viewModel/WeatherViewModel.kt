package com.muryno.openweather.presenter.viewModel

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.muryno.openweather.data.model.ComingDaysWeather
import com.muryno.openweather.data.model.CurrentWeather
import com.muryno.openweather.data.util.Resource
import com.muryno.openweather.domain.useCase.FetchComingDaysWeatherFromApi
import com.muryno.openweather.domain.useCase.FetchCurrentWeatherFromApi
import com.muryno.openweather.presenter.adapter.ForecastWeatherAdapter
import com.muryno.openweather.utils.ShowToast
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import javax.inject.Inject


@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val fetchComingDaysWeatherFromApi: FetchComingDaysWeatherFromApi,
    private val fetchCurrentWeatherFromApi: FetchCurrentWeatherFromApi,
) : ViewModel() {
    @Inject
    lateinit var adapter: ForecastWeatherAdapter


    //initial loading
    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean> = _loading

    //initial loading
     var currentWeathers: MutableLiveData<CurrentWeather> = MutableLiveData<CurrentWeather>()


    //failed
    val failure: MutableLiveData<Boolean> = MutableLiveData()

    val editTextLocation = ObservableField("")


    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.IO)


   fun  fetchWeather(){
       if(!(editTextLocation.get() == "")) {
           _loading.postValue(true)
           editTextLocation.get()?.let { getWeatherData(it) }
           editTextLocation.get()?.let { getSubsequentWeatherData(it) }
       }else{
           ShowToast("Please enter city you want to search for")
           _loading.postValue(false)
       }
    }




    //get world news from api and check if network iss avilable or not
    fun getWeatherData(location: String) {
        coroutineScope.launch {
            try {

                fetchCurrentWeatherFromApi.call(location).collect {
                    when (it) {
                        is Resource.Loading -> {
                            _loading.postValue(true)
                        }
                        is Resource.Success -> {
                            withContext(Dispatchers.Main) {
                                updateView(it.data)
                            }
                        }
                        is Resource.Error -> {
                            withContext(Dispatchers.Main) {
                                _loading.postValue(false)
                                failure.postValue(true)
                                ShowToast(it.error.message ?: "error occur")
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main)
                {
                    ShowToast(e.message.toString())
                }
            }
        }
    }


    fun getSubsequentWeatherData(location: String) {
        coroutineScope.launch {
            try {
                fetchComingDaysWeatherFromApi.call(location).collect {
                    when (it) {
                        is Resource.Loading -> {
                            // if it is empty, load else don't
                            _loading.postValue(true)
                        }
                        is Resource.Success -> {
                            withContext(Dispatchers.Main) {
                                updateSubsequentView(it.data)
                            }
                        }
                        is Resource.Error -> {
                            withContext(Dispatchers.Main) {
                                _loading.postValue(false)
                                failure.postValue(true)
                                ShowToast(it.error.message ?: "error occur")
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main)
                {
                    ShowToast(e.message.toString())
                }
            }
        }
    }

    private fun updateSubsequentView(data: ComingDaysWeather?) {
        if(data!= null) {
            adapter.differ.submitList(data.list)
        }
    }



    private fun updateView(currentWeather:CurrentWeather?){
        if(currentWeather != null) {
            currentWeathers.postValue(currentWeather)
            _loading.postValue(false)
            failure.postValue(false)
        }
    }





    //to prevent memory leakage
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}





