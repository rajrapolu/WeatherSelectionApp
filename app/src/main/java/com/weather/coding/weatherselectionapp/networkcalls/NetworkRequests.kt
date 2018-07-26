package com.weather.coding.weatherselectionapp.networkcalls

import android.arch.lifecycle.MutableLiveData
import com.weather.coding.weatherselectionapp.CurrentWeatherDTO
import com.weather.coding.weatherselectionapp.weatherproviderfactory.WeatherProviderFactory

class NetworkRequests : NetworkCallListener<CurrentWeatherDTO> {
    private var networkRequestsLiveData: MutableLiveData<CurrentWeatherDTO>? = null

    fun getNetworkRequestLiveData(): MutableLiveData<CurrentWeatherDTO> {
        if (networkRequestsLiveData == null) {
            networkRequestsLiveData = MutableLiveData()
        }
        return networkRequestsLiveData as MutableLiveData<CurrentWeatherDTO>
    }

    fun getWeatherInformation(weatherProvider: String?, cityName: String?, countryName: String?, latitude: Double?, longitude: Double?): Boolean {
        if (weatherProvider != null) {
            WeatherProviderFactory().getWeatherProvider(weatherProvider).getWeatherInformation(cityName, countryName, latitude, longitude, this)
            return true
        }
        return false
    }

    override fun onSuccess(model: CurrentWeatherDTO?) {
        networkRequestsLiveData?.value = model
    }

    override fun onFailure() {
        networkRequestsLiveData?.value = null
    }
}