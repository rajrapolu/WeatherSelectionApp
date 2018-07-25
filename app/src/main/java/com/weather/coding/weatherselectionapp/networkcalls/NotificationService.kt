package com.weather.coding.weatherselectionapp.networkcalls

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.weather.coding.weatherselectionapp.CurrentWeatherDTO
import com.weather.coding.weatherselectionapp.Util.NotificationUtil
import com.weather.coding.weatherselectionapp.Util.SharedPreferenceUtil
import com.weather.coding.weatherselectionapp.weatherproviderfactory.WeatherProviderFactory

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
        val weatherProviderName = sharedPreferenceUtil.getSavedWeatherProvider()

        if (weatherProviderName != null) {
            val cityName: String? = sharedPreferenceUtil.getSavedCityName()
            val countryName: String? = sharedPreferenceUtil.getSavedCountryName()
            val latitude: Double? = sharedPreferenceUtil.getSavedLatitude()
            val longitude: Double? = sharedPreferenceUtil.getSavedLongitude()
            val weatherProvider = WeatherProviderFactory().getWeatherProvider(weatherProviderName)
            weatherProvider.getWeatherInformation(cityName, countryName, latitude, longitude, this)
        }
    }
}