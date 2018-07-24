package com.weather.coding.weatherselectionapp.locationinput

import android.app.AlarmManager
import android.app.PendingIntent
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.content.Intent
import com.weather.coding.weatherselectionapp.CurrentWeatherDTO
import com.weather.coding.weatherselectionapp.Util.SharedPreferenceUtil
import com.weather.coding.weatherselectionapp.dataobjects.OpenWeatherModel
import com.weather.coding.weatherselectionapp.networkcalls.NetworkRequests
import com.weather.coding.weatherselectionapp.networkcalls.PeriodicNotificationService

class LocationInputViewModel : ViewModel(), NetworkRequests.NetworkCallListener<CurrentWeatherDTO> {

    override fun onSuccess(model: CurrentWeatherDTO?) {
        mCurrentWeatherDTO?.value = model
    }

    var mCurrentWeatherDTO: MutableLiveData<CurrentWeatherDTO>? = null
    var mLocationWeatherDTO: MutableLiveData<OpenWeatherModel.LocationWeatherDTO>? = null

    fun getOpenWeatherData(): MutableLiveData<OpenWeatherModel.LocationWeatherDTO> {
        if (mLocationWeatherDTO == null) mLocationWeatherDTO = MutableLiveData()
        return mLocationWeatherDTO as MutableLiveData<OpenWeatherModel.LocationWeatherDTO>
    }

    fun getCurrentWeatherData(): MutableLiveData<CurrentWeatherDTO> {
        if (mCurrentWeatherDTO == null) mCurrentWeatherDTO = MutableLiveData()
        return mCurrentWeatherDTO as MutableLiveData<CurrentWeatherDTO>
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
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), AlarmManager.INTERVAL_FIFTEEN_MINUTES, pendingIntent)
    }

    fun getDarkSkyInformation(latitude: Double, longitude: Double) {
        NetworkRequests().getDarkSkyInformation(latitude, longitude, this)
    }

    fun getFiveDayWeatherInformation(cityName: String) {
        NetworkRequests().getFiveDayWeatherInformation(cityName, this)
    }

    fun getWeatherBitInformation(cityName: String) {
        NetworkRequests().getWeatherBitInformation(cityName, this)
    }

    fun saveLocationData(applicationContext: Context, cityName: String, countryName: String) {
        val sharedPreferenceUtil = SharedPreferenceUtil.getInstance(applicationContext)
        sharedPreferenceUtil.saveLocationPref(cityName, countryName)
    }
}