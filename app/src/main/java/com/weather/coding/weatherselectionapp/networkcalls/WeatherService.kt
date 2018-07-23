package com.weather.coding.weatherselectionapp.networkcalls

import com.weather.coding.weatherselectionapp.OpenWeatherModel
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface WeatherService {

    @GET("data/2.5/forecast/")
    fun getOpenWeatherData(@Query("q") cityName: String, @Query("units") units: String, @Query("APPID") apiKey: String): Call<OpenWeatherModel.LocationWeatherDTO>

    @GET("forecast/{apiKey}/{latitude},{longitude}")
    fun getDarkSkyData(@Path("apiKey") apiKey: String, @Path("latitude") latitude: Double, @Path("longitude") longitude: Double): Call<OpenWeatherModel.LocationWeatherDTO>
}