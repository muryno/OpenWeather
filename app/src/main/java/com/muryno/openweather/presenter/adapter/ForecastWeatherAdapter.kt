package com.muryno.openweather.presenter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.muryno.openweather.R
import com.muryno.openweather.data.model.WeatherData
import com.muryno.openweather.databinding.ForecastLayoutBinding
import com.muryno.openweather.utils.*
import javax.inject.Inject


class ForecastWeatherAdapter @Inject constructor() :
    RecyclerView.Adapter<ForecastWeatherAdapter.MyViewHolder>() {


    private val callback = object : DiffUtil.ItemCallback<WeatherData>() {
        override fun areItemsTheSame(
            oldItem: WeatherData,
            newItem: WeatherData
        ): Boolean {
            return oldItem.dt_txt == newItem.dt_txt
        }

        override fun areContentsTheSame(
            oldItem: WeatherData,
            newItem: WeatherData
        ): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, callback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ForecastLayoutBinding  = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.forecast_layout,
            parent,
            false
        )
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val worldNewsItem= differ.currentList[position]
        holder.bind(worldNewsItem)
    }


    inner class MyViewHolder(val binding: ForecastLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(weatherData: WeatherData?) {
            binding.apply {

                val hf = weatherData?.main?.temp?.let { formatTemperature(it) }
                txtTemperature.text =hf.toString()

                cloudCondition.text = weatherData?.weather?.get(0)?.main

                    txtTime.text = getFormattedDate(weatherData?.dt_txt,time)

               txtDate.text = getFormattedDate(weatherData?.dt_txt,day)

                cloudImage.setImageResource(getArtResourceForWeatherCondition(weatherData?.weather?.get(0)?.id ?: 200))
            }
        }
    }

}