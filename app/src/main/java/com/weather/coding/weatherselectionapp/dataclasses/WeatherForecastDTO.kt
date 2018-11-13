package com.weather.coding.weatherselectionapp.dataclasses

data class WeatherForecastDTO(val cityName: String, val dayForecasts: MutableList<DayForecast>)
