package com.weather.coding.weatherselectionapp.dataobjects

object FiveDayWeatherModel {
    data class FiveDayData(val location: String, val temperature: String, val skytext: String, val humidity: String, val wind: String, val date: String, val day: String)
    data class FiveDayWeatherDataDTO(val apiVersion: String, val data: FiveDayData)
}