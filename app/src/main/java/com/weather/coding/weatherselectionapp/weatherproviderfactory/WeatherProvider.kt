package com.weather.coding.weatherselectionapp.weatherproviderfactory

import com.weather.coding.weatherselectionapp.CurrentWeatherDTO
import com.weather.coding.weatherselectionapp.RequiredFields
import com.weather.coding.weatherselectionapp.WeatherForecastModel
import com.weather.coding.weatherselectionapp.networkcalls.NetworkCallListener

abstract class WeatherProvider {
    val weatherForecastDays: Int = 5
    abstract val baseURL: String
    abstract val apiKey: String
    abstract val fieldsRequired: RequiredFields
    abstract val displayName: String
    //this field is used to notify that the weather provider supports weather forecast
    abstract val supportWeatherForecast: Boolean
    abstract fun getWeatherInformation(cityName: String?, countryName: String?, latitude: Double?, longitude: Double?, listener: NetworkCallListener<CurrentWeatherDTO>)
    abstract fun getWeatherForecast(cityName: String?, countryName: String?, latitude: Double?, longitude: Double?, listener: NetworkCallListener<WeatherForecastModel.WeatherForecastDTO>)
}