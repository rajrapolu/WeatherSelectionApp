package com.weather.coding.weatherselectionapp.currentweather

import android.arch.lifecycle.ViewModel
import android.content.Context
import com.weather.coding.weatherselectionapp.Util.SharedPreferenceUtil

class CurrentWeatherViewModel: ViewModel() {

    fun getSavedWeatherProvider(applicationContext: Context): String? {
        return SharedPreferenceUtil.getInstance(applicationContext).getSavedWeatherProvider()
    }
}