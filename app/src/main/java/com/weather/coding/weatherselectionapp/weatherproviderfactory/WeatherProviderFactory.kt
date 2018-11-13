package com.weather.coding.weatherselectionapp.weatherproviderfactory

class WeatherProviderFactory {

    /**
     * Based on the weather provider name a weather provider object is created and returned.
     * By default open weather provider is selected
     *
     * @param weatherProviderName one of the Weather providers
     * @see WeatherProviders
     */
    fun getWeatherProvider(weatherProviderName: String) = when(weatherProviderName) {
            WeatherProviders.OPEN_WEATHER.name -> OpenWeatherProvider()
            WeatherProviders.DARK_SKY.name -> DarkSkyProvider()
            WeatherProviders.FIVE_DAY_WEATHER.name -> FiveDayWeatherProvider()
            WeatherProviders.WEATHER_BIT.name -> WeatherBitProvider()
            else -> OpenWeatherProvider()
        }
}