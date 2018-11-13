package com.weather.coding.weatherselectionapp.networkcalls

import android.arch.lifecycle.MutableLiveData
import com.weather.coding.weatherselectionapp.dataclasses.WeatherForecastDTO
import com.weather.coding.weatherselectionapp.weatherproviderfactory.WeatherProvider

class WeatherForecastNetworkRequests : NetworkCallListener<WeatherForecastDTO> {
    private var forecastRequestLiveData: MutableLiveData<WeatherForecastDTO>? = null

    fun getForecastRequestLiveData(): MutableLiveData<WeatherForecastDTO> {
        if (forecastRequestLiveData == null) {
            forecastRequestLiveData = MutableLiveData()
        }
        return forecastRequestLiveData as MutableLiveData<WeatherForecastDTO>
    }

    fun getWeatherForecast(weatherProvider: WeatherProvider, cityName: String, countryName: String?, latitude: Double?, longitude: Double?) {
        weatherProvider.getWeatherForecast(cityName, countryName, latitude, longitude, this)
    }

    override fun onSuccess(model: WeatherForecastDTO?) {
        forecastRequestLiveData?.value = model
    }

    override fun onFailure() {
        forecastRequestLiveData?.value = null
    }
}