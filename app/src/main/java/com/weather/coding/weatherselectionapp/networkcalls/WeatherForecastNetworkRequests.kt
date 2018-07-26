package com.weather.coding.weatherselectionapp.networkcalls

import android.arch.lifecycle.MutableLiveData
import com.weather.coding.weatherselectionapp.WeatherForecastModel
import com.weather.coding.weatherselectionapp.weatherproviderfactory.WeatherProvider

class WeatherForecastNetworkRequests: NetworkCallListener<WeatherForecastModel.WeatherForecastDTO> {
    private var forecastRequestLiveData: MutableLiveData<WeatherForecastModel.WeatherForecastDTO>? = null

    fun getForecastRequestLiveData(): MutableLiveData<WeatherForecastModel.WeatherForecastDTO> {
        if (forecastRequestLiveData == null) {
            forecastRequestLiveData = MutableLiveData()
        }
        return forecastRequestLiveData as MutableLiveData<WeatherForecastModel.WeatherForecastDTO>
    }

    fun getWeatherForecast(weatherProvider: WeatherProvider, cityName: String, countryName: String?, latitude: Double?, longitude: Double?) {
        weatherProvider.getWeatherForecast(cityName, countryName, latitude, longitude, this)
    }

    override fun onSuccess(model: WeatherForecastModel.WeatherForecastDTO?) {
        forecastRequestLiveData?.value = model
    }

    override fun onFailure() {
        forecastRequestLiveData?.value = null
    }
}