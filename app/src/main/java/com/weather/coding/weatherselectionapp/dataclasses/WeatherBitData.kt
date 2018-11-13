package com.weather.coding.weatherselectionapp.dataclasses

import com.google.gson.annotations.SerializedName

data class WeatherBitData(val temp: Double, @SerializedName("wind_spd") val windSpeed: Double, @SerializedName("city_name") val cityName: String)