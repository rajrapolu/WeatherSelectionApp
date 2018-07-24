package com.weather.coding.weatherselectionapp.weatherproviderfactory

import com.weather.coding.weatherselectionapp.RequiredFields

abstract class WeatherProvider {
    abstract val baseURL: String
    abstract val apiKey: String
    abstract val fieldsRequired: RequiredFields
    abstract val displayName: String
}