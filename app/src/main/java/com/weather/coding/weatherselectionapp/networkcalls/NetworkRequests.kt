package com.weather.coding.weatherselectionapp.networkcalls

import com.weather.coding.weatherselectionapp.CurrentWeatherDTO
import com.weather.coding.weatherselectionapp.weatherproviderfactory.WeatherProviderFactory

class NetworkRequests {

    interface NetworkCallListener<T> {
        fun onSuccess(model: T?)
        fun onFailure()
    }

    fun getWeatherInformation(weatherProvider: String?, cityName: String?, countryName: String?, latitude: Double?, longitude: Double?, listener: NetworkCallListener<CurrentWeatherDTO>): Boolean {
        if (weatherProvider != null) {
            WeatherProviderFactory().getWeatherProvider(weatherProvider).getWeatherInformation(cityName, countryName, latitude, longitude, listener)
            return true
        }
        return false
    }
}