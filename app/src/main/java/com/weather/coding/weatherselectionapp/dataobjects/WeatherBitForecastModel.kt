package com.weather.coding.weatherselectionapp.dataobjects

import com.google.gson.annotations.SerializedName

object WeatherBitForecastModel {
    data class WeatherBitForecastData(val temp: Double, @SerializedName("datetime") val date: String,
                                      @SerializedName("min_temp") val minTemp: Double,
                                      @SerializedName("max_temp") val maxTemp: Double)

    data class WeatherBitForecastDTO(@SerializedName("data") val weatherForecastData: List<WeatherBitForecastData>, @SerializedName("city_name") val cityName: String)
}