package com.weather.coding.weatherselectionapp.dataclasses

import com.google.gson.annotations.SerializedName

data class DarkSkyDTO(val latitude: Double, val longitude: Double,
                      @SerializedName("currently") val darkSkyCurrentWeatherDTO: DarkSkyCurrentWeatherDTO)