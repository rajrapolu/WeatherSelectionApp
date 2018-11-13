package com.weather.coding.weatherselectionapp.dataclasses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WeatherListDTO(@SerializedName("dt") val date: Int,
                          @SerializedName("main") val mainWeather: WeatherMainDTO,
                          val weather: List<WeatherDTO>, val clouds: CloudsDTO,
                          val wind: WindDTO, @SerializedName("dt_txt") val dateText: String) : Parcelable