package com.weather.coding.weatherselectionapp.dataobjects

import com.google.gson.annotations.SerializedName

object WeatherBitModel {
    data class WeatherBitData(val temp: Double, @SerializedName("wind_spd") val windSpeed: Double, @SerializedName("city_name") val cityName: String)
    data class WeatherBitDTO(val data: List<WeatherBitData>, @SerializedName("city_name") val cityName: String)
}