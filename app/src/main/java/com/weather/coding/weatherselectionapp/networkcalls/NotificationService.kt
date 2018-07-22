package com.weather.coding.weatherselectionapp.networkcalls

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.weather.coding.weatherselectionapp.NotificationUtil
import com.weather.coding.weatherselectionapp.OpenWeatherModel

class NotificationService: Service(), NetworkRequests.NetworkCallListener<OpenWeatherModel.LocationWeatherDTO> {

    override fun onSuccess(model: OpenWeatherModel.LocationWeatherDTO?) {
        NotificationUtil.createNotification(applicationContext, model)
        stopSelf()
    }

    override fun onFailure() {
        stopSelf()
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        //TODO remove the hardcoded city values
        NetworkRequests().getOpenWeatherInformation("boston", "us", this)
        return super.onStartCommand(intent, flags, startId)
    }
}