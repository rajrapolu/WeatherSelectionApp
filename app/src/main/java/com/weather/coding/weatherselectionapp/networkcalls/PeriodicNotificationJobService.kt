package com.weather.coding.weatherselectionapp.networkcalls

import android.app.job.JobParameters
import android.app.job.JobService
import com.weather.coding.weatherselectionapp.CurrentWeatherDTO
import com.weather.coding.weatherselectionapp.Util.NotificationUtil
import com.weather.coding.weatherselectionapp.Util.SharedPreferenceUtil
import com.weather.coding.weatherselectionapp.weatherproviderfactory.WeatherProviderFactory

class PeriodicNotificationJobService: JobService(), NetworkCallListener<CurrentWeatherDTO> {
    var jobParams: JobParameters? = null

    override fun onSuccess(model: CurrentWeatherDTO?) {
        if (model != null) {
            NotificationUtil.createNotification(applicationContext, model)
        }
        jobFinished(jobParams, false)
    }

    override fun onFailure() {
        jobFinished(jobParams, false)
    }

    override fun onStopJob(p0: JobParameters?): Boolean {
        //return true to reschedule the failed job
        return true
    }

    override fun onStartJob(params: JobParameters?): Boolean {
        val sharedPreferenceUtil = SharedPreferenceUtil.getInstance(applicationContext)
        val weatherProviderName = sharedPreferenceUtil.getSavedWeatherProvider()

        if (weatherProviderName != null) {
            jobParams = params
            val cityName: String? = sharedPreferenceUtil.getSavedCityName()
            val countryName: String? = sharedPreferenceUtil.getSavedCountryName()
            val latitude: Double? = sharedPreferenceUtil.getSavedLatitude()
            val longitude: Double? = sharedPreferenceUtil.getSavedLongitude()
            val weatherProvider = WeatherProviderFactory().getWeatherProvider(weatherProviderName)
            weatherProvider.getWeatherInformation(cityName, countryName, latitude, longitude, this)
            //returning true to let the service know that we are doing operation in a different thread
            return true
        } else {
            jobFinished(params, false)
        }
        return false
    }
}