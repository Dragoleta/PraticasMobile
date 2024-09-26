package com.weatherapp.model

import android.graphics.Bitmap
import com.google.android.gms.maps.model.LatLng

data class City(
    val name: String,
    var location: LatLng? = null,
    var weather: Weather? = null,
    var imgUrl: String? = null,
    var bitmap: Bitmap? = null
)