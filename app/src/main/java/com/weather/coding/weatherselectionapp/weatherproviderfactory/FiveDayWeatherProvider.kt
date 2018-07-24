package com.weather.coding.weatherselectionapp.weatherproviderfactory

import com.weather.coding.weatherselectionapp.RequiredFields

class FiveDayWeatherProvider: WeatherProvider() {
    override val displayName: String
        get() = "Five Day Weather Api"
    override val baseURL: String
        get() = "https://5dayweather.org/"
    override val apiKey: String
        get() = ""
    override val fieldsRequired: RequiredFields
        get() = RequiredFields.CITY_NAME
}