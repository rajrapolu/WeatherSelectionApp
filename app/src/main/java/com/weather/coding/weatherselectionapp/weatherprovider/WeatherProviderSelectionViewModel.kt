package com.weather.coding.weatherselectionapp.weatherprovider

import android.app.AlarmManager
import android.app.PendingIntent
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.content.Intent
import com.weather.coding.weatherselectionapp.Util.SharedPreferenceUtil
import com.weather.coding.weatherselectionapp.networkcalls.PeriodicNotificationService
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

    fun createPeriodicFetchCall(applicationContext: Context) {
        val sharedPreferenceUtil = SharedPreferenceUtil.getInstance(applicationContext)
        // Setting up the periodic notification calls only if has not been setup
        val intent = Intent(applicationContext, PeriodicNotificationService::class.java)
        val pendingIntent = PendingIntent.getBroadcast(applicationContext, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val alarmManager: AlarmManager = applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), AlarmManager.INTERVAL_HOUR, pendingIntent)
        sharedPreferenceUtil.savePeriodicCallSetUp()
    }

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