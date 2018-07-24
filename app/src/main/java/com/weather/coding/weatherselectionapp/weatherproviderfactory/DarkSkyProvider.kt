package com.weather.coding.weatherselectionapp.weatherproviderfactory

import com.weather.coding.weatherselectionapp.RequiredFields

class DarkSkyProvider: WeatherProvider() {
    override val displayName: String
        get() = "Dark Sky Api"
    override val baseURL: String
        get() = "https://api.darksky.net/"
    override val apiKey: String
        get() = "0a3f349616016d5a625219e7256d6452"
    override val fieldsRequired: RequiredFields
        get() = RequiredFields.LAT_LNG
}