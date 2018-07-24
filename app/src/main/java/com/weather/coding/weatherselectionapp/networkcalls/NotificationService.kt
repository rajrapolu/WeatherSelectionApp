package com.weather.coding.weatherselectionapp.networkcalls

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.weather.coding.weatherselectionapp.CurrentWeatherDTO
import com.weather.coding.weatherselectionapp.Util.NotificationUtil
import com.weather.coding.weatherselectionapp.Util.SharedPreferenceUtil
import com.weather.coding.weatherselectionapp.WeatherProviders

class NotificationService: Service(), NetworkRequests.NetworkCallListener<CurrentWeatherDTO> {
    override fun onSuccess(model: CurrentWeatherDTO?) {
        if (model != null) {
            NotificationUtil.createNotification(applicationContext, model)
        }
        stopSelf()
    }

    override fun onFailure() {
        stopSelf()
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        chooseNetworkOperation()
        return super.onStartCommand(intent, flags, startId)
    }

    private fun chooseNetworkOperation() {
        val sharedPreferenceUtil = SharedPreferenceUtil.getInstance(applicationContext)
        val weatherProvider = sharedPreferenceUtil.getSavedWeatherProvider()
        when (weatherProvider) {
            WeatherProviders.OPEN_WEATHER.name -> {
                if (sharedPreferenceUtil.getSavedCityName() != null && sharedPreferenceUtil.getSavedCountryName() != null) {
                    NetworkRequests().getOpenWeatherInformation(sharedPreferenceUtil.getSavedCityName()!!, sharedPreferenceUtil.getSavedCountryName()!!, this)
                }
            }
            WeatherProviders.DARK_SKY.name -> {
                if (sharedPreferenceUtil.getSavedLatitude() != null && sharedPreferenceUtil.getSavedLongitude() != null) {
                    NetworkRequests().getDarkSkyInformation(sharedPreferenceUtil.getSavedLatitude()!!, sharedPreferenceUtil.getSavedLongitude()!!, this)
                }
            }
            WeatherProviders.FIVE_DAY_WEATHER.name -> {
                if (sharedPreferenceUtil.getSavedCityName() != null) {
                    NetworkRequests().getFiveDayWeatherInformation(sharedPreferenceUtil.getSavedCityName()!!, this)
                }
            }
            WeatherProviders.WEATHER_BIT.name -> {
                if (sharedPreferenceUtil.getSavedCityName() != null) {
                    NetworkRequests().getWeatherBitInformation(sharedPreferenceUtil.getSavedCityName()!!, this)
                }
            }
        }
    }
}