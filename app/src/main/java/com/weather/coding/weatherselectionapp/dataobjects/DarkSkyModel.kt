package com.weather.coding.weatherselectionapp.dataobjects

import com.google.gson.annotations.SerializedName

object DarkSkyModel {
    data class DarkSkyCurrentWeatherDTO(val temperature: Double, val windSpeed: Double)
    data class DarkSkyDTO(val latitude: Double, val longitude: Double, @SerializedName("currently") val darkSkyCurrentWeatherDTO: DarkSkyCurrentWeatherDTO)
}