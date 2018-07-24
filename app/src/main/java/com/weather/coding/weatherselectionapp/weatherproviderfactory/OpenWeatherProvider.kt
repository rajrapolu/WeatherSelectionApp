package com.weather.coding.weatherselectionapp.weatherproviderfactory

import com.weather.coding.weatherselectionapp.RequiredFields

class OpenWeatherProvider: WeatherProvider() {
    override val displayName: String
        get() = "Open Weather Api"
    override val apiKey: String
        get() = "f12e77308bfba46a4939b7de916db179"
    override val fieldsRequired: RequiredFields
        get() = RequiredFields.CITY_COUNTRY_NAME
    override val baseURL: String
        get() = "http://api.openweathermap.org/"
}