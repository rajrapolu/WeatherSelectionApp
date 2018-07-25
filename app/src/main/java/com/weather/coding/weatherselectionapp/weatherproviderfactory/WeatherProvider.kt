package com.weather.coding.weatherselectionapp.weatherproviderfactory

import com.weather.coding.weatherselectionapp.CurrentWeatherDTO
import com.weather.coding.weatherselectionapp.RequiredFields
import com.weather.coding.weatherselectionapp.networkcalls.NetworkRequests

abstract class WeatherProvider {
    abstract val baseURL: String
    abstract val apiKey: String
    abstract val fieldsRequired: RequiredFields
    abstract val displayName: String
    abstract fun getWeatherInformation(cityName: String?, countryName: String?, latitude: Double?, longitude: Double?, listener: NetworkRequests.NetworkCallListener<CurrentWeatherDTO>)
}