package com.weather.coding.weatherselectionapp.Util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import com.weather.coding.weatherselectionapp.CurrentWeatherDTO
import com.weather.coding.weatherselectionapp.R
import com.weather.coding.weatherselectionapp.currentweather.CurrentWeatherActivity

private const val CHANNEL_ID = "NOTIFICATION_CHANNEL"
private const val NOTIFICATION_ID = 1
private const val PENDING_INTENT_REQUEST_CODE = 999

class NotificationUtil {

    companion object {


        /**
         * Creates notification channel. As a default we attach CHANNEL_ID as the id for notification channel
         * @param applicationContext
         */
        fun createNotificationChannel(applicationContext: Context) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val notificationChannel = NotificationChannel(CHANNEL_ID, "All_Channel", NotificationManager.IMPORTANCE_DEFAULT)
                notificationChannel.description = "This is the only channel for notifications"
                val notificationManager: NotificationManager = applicationContext.getSystemService(NotificationManager::class.java)
                notificationManager.createNotificationChannel(notificationChannel)
            }
        }

        /**
         * Creates notification. We give all the notifications the same id so that we can display
         * only a single notification at a time
         * @param applicationContext
         * @param model for LocationWeatherDTO
         */
        fun createNotification(applicationContext: Context, model: CurrentWeatherDTO) {
            val intent = Intent(applicationContext, CurrentWeatherActivity::class.java)
            intent.putExtra(CurrentWeatherActivity.LOCATION_WEATHER_KEY, model)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            val pendingIntent = PendingIntent.getActivity(applicationContext, PENDING_INTENT_REQUEST_CODE, intent, 0)
            val notificationBuilder: NotificationCompat.Builder =
                    NotificationCompat.Builder(applicationContext, CHANNEL_ID)
                            .setSmallIcon(R.drawable.ic_beach_access_black)
                            .setContentTitle(applicationContext.getString(R.string.notification_title))
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                            .setContentIntent(pendingIntent)
                            .setAutoCancel(true)

            val notificationSubText = createNotificationText(applicationContext, model.currentTemp, model.location)
            if (notificationSubText != null) {
                notificationBuilder.setContentText(notificationSubText)
                val notificationManager = NotificationManagerCompat.from(applicationContext)
                notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
            }
        }

        private fun createNotificationText(context: Context, currenTemp: String, cityName: String?): CharSequence? {
            return context.getString(R.string.notification_text, currenTemp, UnitsUtil.getFahrenheitUnit(), cityName)
        }
    }
}