package com.weather.coding.weatherselectionapp.dataclasses

import com.google.gson.annotations.SerializedName

data class WeatherBitForecastDTO(@SerializedName("data") val weatherForecastData: List<WeatherBitForecastData>,
                            @SerializedName("city_name") val cityName: String)