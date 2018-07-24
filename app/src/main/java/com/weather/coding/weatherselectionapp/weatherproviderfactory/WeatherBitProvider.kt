package com.weather.coding.weatherselectionapp.weatherproviderfactory

import com.weather.coding.weatherselectionapp.RequiredFields

class WeatherBitProvider: WeatherProvider() {
    override val baseURL: String
        get() = "https://api.weatherbit.io/"
    override val apiKey: String
        get() = "fd2bd93ad0fd45faba9a8f658fab3cdf"
    override val fieldsRequired: RequiredFields
        get() = RequiredFields.CITY_NAME
    override val displayName: String
        get() = "WeatherBit Api"
}