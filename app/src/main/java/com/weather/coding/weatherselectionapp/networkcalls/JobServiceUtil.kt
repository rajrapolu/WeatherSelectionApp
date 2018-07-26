package com.weather.coding.weatherselectionapp.networkcalls

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context

//private const val MILLISECONDS_IN_HOUR: Long = 3000
private const val MILLISECONDS_IN_HOUR: Long = 3600000
private const val JOB_ID = 1
class JobServiceUtil {
    companion object {
        fun schedulePeriodicJob(applicationContext: Context) {
            val jobScheduler: JobScheduler = applicationContext.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
            jobScheduler.schedule(JobInfo.Builder(JOB_ID, ComponentName(applicationContext,
                    PeriodicNotificationJobService::class.java))
                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                    .setPeriodic(MILLISECONDS_IN_HOUR)
                    .build())
        }
    }
}