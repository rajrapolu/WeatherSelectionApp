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

class WeatherProviderSelectionViewModel: ViewModel() {

    fun getWeatherProvider(weatherProviderKey: String): WeatherProvider {
        return WeatherProviderFactory().getWeatherProvider(weatherProviderKey)
    }

    fun saveWeatherProviderPref(applicationContext: Context, weatherProvider: String) {
        SharedPreferenceUtil.getInstance(applicationContext).saveWeatherProviderPref(weatherProvider)
    }

    fun createPeriodicFetchCall(applicationContext: Context) {
        val sharedPreferenceUtil = SharedPreferenceUtil.getInstance(applicationContext)
        // Setting up the periodic notification calls only if has not been setup
        if (sharedPreferenceUtil.didPeriodicCallSetUpDone() != null && !sharedPreferenceUtil.didPeriodicCallSetUpDone()!!) {
            val intent = Intent(applicationContext, PeriodicNotificationService::class.java)
            val pendingIntent = PendingIntent.getBroadcast(applicationContext, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            val alarmManager: AlarmManager = applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            //TODO change 30000 to one day interval
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 3000, pendingIntent)
            sharedPreferenceUtil.savePeriodicCallSetUp()
        }
    }
}