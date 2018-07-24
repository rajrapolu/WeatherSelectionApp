package com.weather.coding.weatherselectionapp.weatherprovider

import android.arch.lifecycle.ViewModel
import android.content.Context
import com.weather.coding.weatherselectionapp.Util.SharedPreferenceUtil
import com.weather.coding.weatherselectionapp.weatherproviderfactory.WeatherProvider
import com.weather.coding.weatherselectionapp.weatherproviderfactory.WeatherProviderFactory

class WeatherProviderSelectionViewModel: ViewModel() {

    fun getWeatherProvider(weatherProviderKey: String): WeatherProvider {
        return WeatherProviderFactory().getWeatherProvider(weatherProviderKey)
    }

    fun saveWeatherProviderPref(applicationContext: Context, weatherProvider: String) {
        SharedPreferenceUtil.getInstance(applicationContext).saveWeatherProviderPref(weatherProvider)
    }
}