package com.weather.coding.weatherselectionapp.locationinput

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import com.weather.coding.weatherselectionapp.CurrentWeatherDTO
import com.weather.coding.weatherselectionapp.Util.SharedPreferenceUtil
import com.weather.coding.weatherselectionapp.networkcalls.NetworkRequests

class LocationInputViewModel : ViewModel(), NetworkRequests.NetworkCallListener<CurrentWeatherDTO> {
    var mCurrentWeatherDTO: MutableLiveData<CurrentWeatherDTO>? = null

    override fun onSuccess(model: CurrentWeatherDTO?) {
        mCurrentWeatherDTO?.value = model
    }

    override fun onFailure() {
        mCurrentWeatherDTO?.value = null
    }

    fun getCurrentWeatherData(): MutableLiveData<CurrentWeatherDTO> {
        if (mCurrentWeatherDTO == null) mCurrentWeatherDTO = MutableLiveData()
        return mCurrentWeatherDTO as MutableLiveData<CurrentWeatherDTO>
    }

    fun saveLocationData(applicationContext: Context, cityName: String?, countryName: String?) {
        SharedPreferenceUtil.getInstance(applicationContext).saveLocationPref(cityName, countryName)
    }

    fun saveLatLngData(applicationContext: Context, latitude: Double, longitude: Double) {
        SharedPreferenceUtil.getInstance(applicationContext).saveLocationLatLngPref(latitude, longitude)
    }

    fun getWeatherInformation(applicationContext: Context, cityName: String?, countryName: String?, latitude: Double?, longitude: Double?): Boolean {
        return NetworkRequests().getWeatherInformation(fetchWeatherProvider(applicationContext), cityName, countryName, latitude, longitude, this)
    }

    fun fetchWeatherProvider(applicationContext: Context): String? {
        return SharedPreferenceUtil.getInstance(applicationContext).getSavedWeatherProvider()
    }
}