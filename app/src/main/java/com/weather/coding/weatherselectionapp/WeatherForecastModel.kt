package com.weather.coding.weatherselectionapp

object WeatherForecastModel {

    data class DayForecast(val temp: String, val minTemp: String, val maxTemp: String, val date: String)
    data class WeatherForecastDTO(val cityName: String, val dayForecasts: MutableList<DayForecast>)
}