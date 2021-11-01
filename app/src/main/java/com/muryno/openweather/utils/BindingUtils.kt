package com.muryno.openweather.utils

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.Nullable
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("imagePaths", "pathError", "imageOption")
fun loadImage(
    imageView: ImageView,
    @Nullable path: String?,
    errorDrawable: Drawable,
    option: String
) {
    var myOptions = RequestOptions()
        .placeholder(errorDrawable)
        .error(errorDrawable)


    when (option) {
        "fit" -> myOptions = myOptions.fitCenter()
        "inside" -> myOptions = myOptions.centerInside()
        "crop" -> myOptions = myOptions.centerCrop()
    }

    Glide.with(imageView.context)
        .load(path ?: "")
        .apply(myOptions)
        .into(imageView)
}


//@BindingAdapter("addTextChangeListener")
//fun addTextChangeListener(view: SearchView, @Nullable WeatherWeatherViewModel: WeatherViewModel?) {
//
//    view.setOnQueryTextListener(
//        object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(p0: String?): Boolean {
//                return false
//            }
//
//            override fun onQueryTextChange(p0: String?): Boolean {
//                if (p0?.isNotEmpty() == true)
////                    WeatherWeatherViewModel?.apply {
////                        WeatherResult.filter { it.model?.lowercase()?.contains(p0.lowercase()) }
////                            .let { tt -> adapter.differ.submitList(tt)
////                                showEmptyView(WeatherResult.isEmpty())
////                    } }
//                else
//                    WeatherWeatherViewModel?.onCreate()
//                return false
//            }
//        }
//    )
//
//    view.setOnCloseListener {
//        WeatherWeatherViewModel?.onCreate()
//        false
//    }
//
//}
