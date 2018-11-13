package com.weather.coding.weatherselectionapp.locationinput

import android.arch.core.util.Function
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.content.Context
import com.weather.coding.weatherselectionapp.dataclasses.CurrentWeatherDTO
import com.weather.coding.weatherselectionapp.util.SharedPreferenceUtil
import com.weather.coding.weatherselectionapp.networkcalls.NetworkRequests

class LocationInputViewModel : ViewModel() {
    var mCurrentWeatherDTO: MutableLiveData<CurrentWeatherDTO>? = null
    var mNetworkRequests = NetworkRequests()
    var mNetworkRequestLiveData: MutableLiveData<CurrentWeatherDTO>? = null
    var mResponseLiveData = MutableLiveData<CurrentWeatherDTO>()

    /**
     * Observing on this live data helps in observing the changes made on livedata object between
     * viewmodel and network request
     */
    fun getCurrentWeatherData(): MutableLiveData<CurrentWeatherDTO> {
        mNetworkRequestLiveData = mNetworkRequests.getNetworkRequestLiveData()
        mCurrentWeatherDTO = Transformations.switchMap(mNetworkRequestLiveData as MutableLiveData, object : Function<CurrentWeatherDTO, LiveData<CurrentWeatherDTO>> {
            override fun apply(currentweather: CurrentWeatherDTO?): LiveData<CurrentWeatherDTO> {
                mResponseLiveData.value = currentweather
                return mResponseLiveData
            }
        }) as MutableLiveData<CurrentWeatherDTO>
        return mCurrentWeatherDTO as MutableLiveData<CurrentWeatherDTO>
    }

    fun saveLocationData(applicationContext: Context, cityName: String?, countryName: String?) {
        SharedPreferenceUtil.getInstance(applicationContext).saveLocationPref(cityName, countryName)
    }

    fun saveLatLngData(applicationContext: Context, latitude: Double, longitude: Double) {
        SharedPreferenceUtil.getInstance(applicationContext).saveLocationLatLngPref(latitude, longitude)
    }

    fun getWeatherInformation(applicationContext: Context, cityName: String?, countryName: String?, latitude: Double?, longitude: Double?): Boolean {
        return mNetworkRequests.getWeatherInformation(fetchWeatherProvider(applicationContext), cityName, countryName, latitude, longitude)
    }

    fun fetchWeatherProvider(applicationContext: Context): String? {
        return SharedPreferenceUtil.getInstance(applicationContext).getSavedWeatherProvider()
    }
}