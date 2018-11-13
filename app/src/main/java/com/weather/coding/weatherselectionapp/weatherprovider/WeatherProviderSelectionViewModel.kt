package com.weather.coding.weatherselectionapp.weatherprovider

import android.arch.lifecycle.ViewModel
import android.content.Context
import com.weather.coding.weatherselectionapp.Util.SharedPreferenceUtil
import com.weather.coding.weatherselectionapp.networkcalls.JobServiceUtil
import com.weather.coding.weatherselectionapp.weatherproviderfactory.WeatherProvider
import com.weather.coding.weatherselectionapp.weatherproviderfactory.WeatherProviderFactory

class WeatherProviderSelectionViewModel : ViewModel() {
    private var selectedRadioButton: String? = null

    fun getWeatherProvider(weatherProviderKey: String): WeatherProvider {
        return WeatherProviderFactory().getWeatherProvider(weatherProviderKey)
    }

    fun saveWeatherProviderPref(applicationContext: Context, weatherProvider: String) {
        SharedPreferenceUtil.getInstance(applicationContext).saveWeatherProviderPref(weatherProvider)
    }

    fun createPeriodicFetchCall(context: Context) {
        JobServiceUtil.schedulePeriodicJob(context)
    }

    // Save the radio button selection so that it persists configuration change
    fun saveSelectedRadioButton(weatherProvider: String) {
        selectedRadioButton = weatherProvider
    }

    fun getSavedSelectedRadioButton(): String? {
        return selectedRadioButton
    }

    fun getSavedWeatherProvider(applicationContext: Context): String? {
        return SharedPreferenceUtil.getInstance(applicationContext).getSavedWeatherProvider()
    }
}