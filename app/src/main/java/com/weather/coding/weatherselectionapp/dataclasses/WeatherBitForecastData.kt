package com.weather.coding.weatherselectionapp.dataclasses

import com.google.gson.annotations.SerializedName

data class WeatherBitForecastData(val temp: Double, @SerializedName("datetime") val date: String,
                             @SerializedName("min_temp") val minTemp: Double,
                             @SerializedName("max_temp") val maxTemp: Double)