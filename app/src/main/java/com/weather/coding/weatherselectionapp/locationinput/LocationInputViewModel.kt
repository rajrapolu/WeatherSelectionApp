package com.weather.coding.weatherselectionapp.locationinput

import android.app.AlarmManager
import android.app.PendingIntent
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.content.Intent
import com.weather.coding.weatherselectionapp.OpenWeatherModel
import com.weather.coding.weatherselectionapp.networkcalls.NetworkRequests
import com.weather.coding.weatherselectionapp.networkcalls.PeriodicNotificationService

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

    fun createPeriodicFetchCall(applicationContext: Context) {
        val intent = Intent(applicationContext, PeriodicNotificationService::class.java)
        val pendingIntent = PendingIntent.getBroadcast(applicationContext, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val alarmManager: AlarmManager = applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        //TODO change 30000 to one day interval
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 30000, pendingIntent)
    }
}