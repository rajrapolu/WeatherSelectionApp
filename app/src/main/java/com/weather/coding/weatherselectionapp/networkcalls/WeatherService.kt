package com.weather.coding.weatherselectionapp.networkcalls

import com.weather.coding.weatherselectionapp.OpenWeatherModel
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("data/2.5/forecast/")
    fun getOpenWeatherData(@Query("q") cityName: String, @Query("APPID") apiKey: String): Call<OpenWeatherModel.LocationWeatherDTO>
}