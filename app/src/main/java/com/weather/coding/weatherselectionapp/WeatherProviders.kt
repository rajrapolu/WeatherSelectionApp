package com.weather.coding.weatherselectionapp

import com.weather.coding.weatherselectionapp.Util.SharedPreferenceUtil

/**
 * Enum class for different apis
 * @param baseURL for the api
 * @param apiKey associated with the api
 */
enum class WeatherProviders(val apiName: String, val baseURL: String, val apiKey: String) {
    OPEN_WEATHER("Open Weather API", "http://api.openweathermap.org/", "f12e77308bfba46a4939b7de916db179"),
    DARK_SKY("Dark Sky API", "https://api.darksky.net/", "0a3f349616016d5a625219e7256d6452"),
    FIVE_WEATHER("5Weather", "csdvdfgvdf", "")
}