package com.weather.coding.weatherselectionapp

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CurrentWeatherDTO(val currentTemp: Double, val location: String, val windSpeed: Double?, val windDirection: Double?, val minTemp: Double?, val maxTemp: Double?): Parcelable