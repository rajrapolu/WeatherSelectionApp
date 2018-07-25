package com.weather.coding.weatherselectionapp.networkcalls

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitService {

    private var mWeatherService: WeatherService? = null

    private val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        this.level = HttpLoggingInterceptor.Level.BODY
    }

    val okHttpClient: OkHttpClient = OkHttpClient.Builder().apply {
        this.addInterceptor(interceptor)
    }.build()

    fun getWeatherService(baseUrl: String): WeatherService {
        mWeatherService = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
                .create(WeatherService::class.java)

        return mWeatherService!!
    }
}