package com.weather.coding.weatherselectionapp.currentweather

import android.arch.lifecycle.ViewModel
import android.content.Context
import com.weather.coding.weatherselectionapp.dataclasses.CurrentWeatherDTO
import com.weather.coding.weatherselectionapp.util.SharedPreferenceUtil
import com.weather.coding.weatherselectionapp.weatherproviderfactory.WeatherProvider
import com.weather.coding.weatherselectionapp.weatherproviderfactory.WeatherProviderFactory

class CurrentWeatherViewModel: ViewModel() {
    private var mWeatherFactory: WeatherProviderFactory = WeatherProviderFactory()
    private var mCurrentWeather: CurrentWeatherDTO? = null

    fun getSavedWeatherProvider(applicationContext: Context): String? {
        return SharedPreferenceUtil.getInstance(applicationContext).getSavedWeatherProvider()
    }

    fun getWeatherProviderFactoryObject(applicationContext: Context): WeatherProvider? {
        val weatherProvider = getSavedWeatherProvider(applicationContext)
        if (weatherProvider != null) {
            return mWeatherFactory.getWeatherProvider(weatherProvider)
        }
        return null
    }

    fun saveCurrentWeather(currentWeatherDTO: CurrentWeatherDTO) {
        mCurrentWeather = currentWeatherDTO
    }
}