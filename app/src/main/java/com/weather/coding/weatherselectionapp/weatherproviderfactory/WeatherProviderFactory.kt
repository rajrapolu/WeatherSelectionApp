package com.weather.coding.weatherselectionapp.weatherproviderfactory

import com.weather.coding.weatherselectionapp.WeatherProviders

class WeatherProviderFactory {

    fun getWeatherProvider(weatherProviderName: String): WeatherProvider {
        when(weatherProviderName) {
            WeatherProviders.OPEN_WEATHER.name -> return OpenWeatherProvider()
            WeatherProviders.DARK_SKY.name -> return DarkSkyProvider()
            WeatherProviders.FIVE_DAY_WEATHER.name -> return FiveDayWeatherProvider()
            else -> return OpenWeatherProvider()
        }
    }
}