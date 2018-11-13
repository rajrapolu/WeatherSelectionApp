package com.weather.coding.weatherselectionapp.dataclasses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LocationWeatherDTO(@SerializedName("cod") val coordinates: String, val message: Double,
                              @SerializedName("cnt") val count: Int,
                              @SerializedName("list") val weatherListDTO: List<WeatherListDTO>,
                              val city: CityDTO): Parcelable