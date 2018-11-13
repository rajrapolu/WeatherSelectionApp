package com.weather.coding.weatherselectionapp.dataclasses

import com.google.gson.annotations.SerializedName

data class WeatherBitDTO(val data: List<WeatherBitData>, @SerializedName("city_name") val cityName: String)