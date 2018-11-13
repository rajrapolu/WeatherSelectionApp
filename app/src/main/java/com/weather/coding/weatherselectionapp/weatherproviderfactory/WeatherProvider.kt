package com.weather.coding.weatherselectionapp.weatherproviderfactory

import com.weather.coding.weatherselectionapp.dataclasses.CurrentWeatherDTO
import com.weather.coding.weatherselectionapp.dataclasses.WeatherForecastDTO
import com.weather.coding.weatherselectionapp.networkcalls.NetworkCallListener

abstract class WeatherProvider {
    // Number of days for which we need the weather forecast
    val weatherForecastDays: Int = 5
    abstract val baseURL: String
    abstract val apiKey: String
    /**
     * Required fields to get the weather data
     * @see RequiredFields
     */
    abstract val fieldsRequired: RequiredFields
    // That needs to displayed when the weather provider is selected
    abstract val displayName: String
    // This field is used to notify that the weather provider supports weather forecast
    abstract val supportWeatherForecast: Boolean

    /**
     * Network call to get the current weather data
     * @param cityName city name if the provider needs one otherwise null
     * @param countryName country name if the providers needs one otherwise null
     * @param latitude latitude of the location
     * @param longitude longitude of the location
     * @param listener callback to notify network requests
     */
    abstract fun getWeatherInformation(cityName: String?, countryName: String?, latitude: Double?, longitude: Double?, listener: NetworkCallListener<CurrentWeatherDTO>)

    /**
     * Network call to get the weather forecast for the next couple of days
     * @param cityName city name if the provider needs one otherwise null
     * @param countryName country name if the providers needs one otherwise null
     * @param latitude latitude of the location
     * @param longitude longitude of the location
     * @param listener callback to notify network requests
     */
    abstract fun getWeatherForecast(cityName: String?, countryName: String?, latitude: Double?, longitude: Double?, listener: NetworkCallListener<WeatherForecastDTO>)
}