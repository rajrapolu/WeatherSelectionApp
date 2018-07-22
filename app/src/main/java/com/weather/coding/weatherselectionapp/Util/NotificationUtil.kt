package com.weather.coding.weatherselectionapp.Util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import com.weather.coding.weatherselectionapp.OpenWeatherModel
import com.weather.coding.weatherselectionapp.R

class NotificationUtil {

    companion object {
        val CHANNEL_ID = "NOTIFICATION_CHANNEL"
        val NOTIFICATION_ID = 1

        /**
         * Creates notification channel. As a default we attach CHANNEL_ID as the id for notification channel
         * @param applicationContext
         */
        fun createNotificationChannel(applicationContext: Context) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val notificationChannel = NotificationChannel(CHANNEL_ID, "All_Channel", NotificationManager.IMPORTANCE_DEFAULT)
                notificationChannel.description = "This is the only channel for notifications"
                val notificationManager : NotificationManager = applicationContext.getSystemService(NotificationManager::class.java)
                notificationManager.createNotificationChannel(notificationChannel)
            }
        }

        /**
         * Creates notification. We give all the notifications the same id so that we can display
         * only a single notification at a time
         * @param applicationContext
         * @param model for LocationWeatherDTO
         */
        fun createNotification(applicationContext: Context, model: OpenWeatherModel.LocationWeatherDTO?) {
            val notificationBuilder: NotificationCompat.Builder =
                    NotificationCompat.Builder(applicationContext, CHANNEL_ID)
                            .setSmallIcon(R.drawable.ic_beach_access_black)
                            .setContentTitle(applicationContext.getString(R.string.notification_title))
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            val notificationSubText = createNotificationText(applicationContext, model?.weatherListDTO, model?.city?.name)
            if (notificationSubText != null) {
                notificationBuilder.setContentText(notificationSubText)
                val notificationManager = NotificationManagerCompat.from(applicationContext)
                notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
            }
        }

        private fun createNotificationText(context: Context, weatherList: List<OpenWeatherModel.WeatherListDTO>?, cityName: String?): CharSequence? {
            if (weatherList != null && weatherList.size > 1) {
                return context.getString(R.string.notification_text, weatherList[0].mainWeather?.temp, UnitsUtil.getFahrenheitUnit(), cityName)
            }
            return null
        }
    }
}