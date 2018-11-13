package com.weather.coding.weatherselectionapp.networkcalls

import com.weather.coding.weatherselectionapp.dataclasses.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherService {

    @GET("data/2.5/forecast/")
    fun getOpenWeatherData(@Query("q") cityName: String, @Query("units") units: String, @Query("APPID") apiKey: String): Call<LocationWeatherDTO>

    @GET("forecast/{apiKey}/{latitude},{longitude}")
    fun getDarkSkyData(@Path("apiKey") apiKey: String, @Path("latitude") latitude: Double, @Path("longitude") longitude: Double): Call<DarkSkyDTO>

    @GET("api.php")
    fun getFiveDayWeatherData(@Query("city") cityName: String): Call<FiveDayWeatherDataDTO>

    @GET("v2.0/current")
    fun getWeatherBitData(@Query("city") cityName: String, @Query("units") units: String, @Query("key") apiKey: String): Call<WeatherBitDTO>

    @GET("v2.0/forecast/daily")
    fun getWeatherBitForecast(@Query("city") cityName: String, @Query("units") units: String, @Query("days") forecastDays: Int, @Query("key") apiKey: String): Call<WeatherBitForecastDTO>
}