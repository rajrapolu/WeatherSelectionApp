package com.weather.coding.weatherselectionapp.locationinput

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.weather.coding.weatherselectionapp.OpenWeatherModel
import com.weather.coding.weatherselectionapp.networkcalls.NetworkRequests

class LocationInputViewModel: ViewModel(), NetworkRequests.NetworkCallListener<OpenWeatherModel.LocationWeatherDTO> {
    var mLocationWeatherDTO: MutableLiveData<OpenWeatherModel.LocationWeatherDTO>? = null

    fun getOpenWeatherData(): MutableLiveData<OpenWeatherModel.LocationWeatherDTO> {
        if (mLocationWeatherDTO == null) mLocationWeatherDTO = MutableLiveData()
        return mLocationWeatherDTO as MutableLiveData<OpenWeatherModel.LocationWeatherDTO>
    }

    override fun onSuccess(model: OpenWeatherModel.LocationWeatherDTO?) {
        mLocationWeatherDTO?.value = model
    }

    override fun onFailure() {

    }

    fun getOpenWeatherInformation(cityName: String, countryName: String) {
        NetworkRequests().getOpenWeatherInformation(cityName, countryName, this)
    }
}