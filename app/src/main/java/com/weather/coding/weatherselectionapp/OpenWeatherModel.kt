package com.weather.coding.weatherselectionapp

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

object OpenWeatherModel {

    @Parcelize
    data class CityDTO(val id: Int, val name: String, @SerializedName("coord") val coordinates: CoordinatesDTO,
                       val country: String, val population: Int): Parcelable
    @Parcelize
    data class CloudsDTO(val all: Int): Parcelable
    @Parcelize
    data class CoordinatesDTO(val lat: Double, val lon: Double): Parcelable
    @Parcelize
    data class WeatherDTO(val id: Int, val main: String, val description: String, val icon: String): Parcelable
    @Parcelize
    data class WeatherListDTO(@SerializedName("dt") val date: Int,
                              @SerializedName("main") val mainWeather: WeatherMainDTO,
                              val weather: List<WeatherDTO>, val clouds: CloudsDTO,
                              val wind: WindDTO, @SerializedName("dt_txt") val dateText: String): Parcelable
    @Parcelize
    data class WeatherMainDTO(val temp: Double, @SerializedName("temp_min") val tempMin: Double,
                              @SerializedName("temp_max")val tempMax: Double, val pressure: Double,
                              @SerializedName("sea_level") val seaLevel: Double,
                              @SerializedName("grnd_level") val groundLevel: Double,
                              val humidity: Int, @SerializedName("temp_kf") val tempKf: Double): Parcelable
    @Parcelize
    data class WindDTO(var speed: Double, @SerializedName("deg") var degree: Double): Parcelable
    @Parcelize
    data class LocationWeatherDTO(@SerializedName("cod") val coordinates: String, val message: Double,
                                  @SerializedName("cnt") val count: Int,
                                  @SerializedName("list") val weatherListDTO: List<WeatherListDTO>,
                                  val city: CityDTO): Parcelable
}