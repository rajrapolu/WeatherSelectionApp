package com.weather.coding.weatherselectionapp

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CurrentWeatherDTO(val currentTemp: String, val location: String, val windSpeed: String?, val windDirection: String?, val minTemp: String?, val maxTemp: String?): Parcelable