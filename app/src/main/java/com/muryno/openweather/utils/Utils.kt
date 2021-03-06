package com.muryno.openweather.utils


import android.util.Log
import android.widget.Toast
import com.muryno.openweather.App
import com.muryno.openweather.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


fun ShowToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(App.instance?.applicationContext, message, duration).show()
}


fun getCurrentDate(): String{
    val sdf = SimpleDateFormat("EEE dd MMM HH:mm", Locale.getDefault())
    return sdf.format(Date())
}


fun getArtResourceForWeatherCondition(weatherId: Int?): Int {

    if (weatherId in 200..232) {
        return R.drawable.art_storm
    } else if (weatherId in 300..321) {
        return R.drawable.art_light_rain
    } else if (weatherId in 500..504) {
        return R.drawable.art_rain
    } else if (weatherId == 511) {
        return R.drawable.art_snow
    } else if (weatherId in 520..531) {
        return R.drawable.art_rain
    } else if (weatherId in 600..622) {
        return R.drawable.art_snow
    } else if (weatherId in 701..761) {
        return R.drawable.art_fog
    } else if (weatherId == 761 || weatherId == 781) {
        return R.drawable.art_storm
    } else if (weatherId == 800) {
        return R.drawable.art_clear
    } else if (weatherId == 801) {
        return R.drawable.art_light_clouds
    } else if (weatherId in 802..804) {
        return R.drawable.art_clouds
    }


    return R.drawable.art_clouds
}


 fun convertCelciusToFahrenheit(celsius: Double): Double {
    return celsius * 9 / 5 + 32
}


fun formatTemperature(temperature: Double): String? {
    return App.instance?.getString(R.string.format_temperature)?.let { String.format(it, temperature * 1.8 + 32) }
}

const val day = 1
const val month = 2
const val time = 3


fun getFormattedDate( dt : String?,id : Int) : String{
    // i pre define this my self with my id
    //1 - you want day
    //2 - you want month
    // 3 - you want time
    val inputFormat = "yyyy-MM-dd HH:mm"
    val outputFormatTime = "EEE dd MMM HH:mm"
    val DATE_TIME_ONLY = SimpleDateFormat(outputFormatTime, Locale.getDefault())
    if (!dt.isNullOrEmpty()) {
        try {
            val parseDate = SimpleDateFormat(inputFormat, Locale.getDefault()).parse(dt)
            if(parseDate!= null) {
                val clean = DATE_TIME_ONLY.format(parseDate)
                //"Sat Mar 21 03:00"
                val days = clean.split(" ")[0]
                val months = clean.split(" ")[1] + " " + clean.split(" ")[2]
                val times = clean.split(" ")[3]
                return when (id) {
                    day -> {
                        days
                    }
                    month -> {
                        months
                    }
                    time -> {
                        times
                    }
                    else -> {
                        return "03:00"
                    }
                }
            }

        } catch (e: ParseException) {
            Log.e("FormatFormDate", Log.getStackTraceString(e))
        }
    }
    return " "
}