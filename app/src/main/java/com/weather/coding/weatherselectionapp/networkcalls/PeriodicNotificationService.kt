package com.weather.coding.weatherselectionapp.networkcalls

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class PeriodicNotificationService: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent) {
        Toast.makeText(context, "Alarm !!", Toast.LENGTH_LONG).show()
        val notificationIntent = Intent(context, NotificationService::class.java)
        context?.startService(notificationIntent)
    }
}