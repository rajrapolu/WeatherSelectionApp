package com.weather.coding.weatherselectionapp.dataclasses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WeatherMainDTO(val temp: Double, @SerializedName("temp_min") val tempMin: Double,
                          @SerializedName("temp_max")val tempMax: Double, val pressure: Double,
                          @SerializedName("sea_level") val seaLevel: Double,
                          @SerializedName("grnd_level") val groundLevel: Double,
                          val humidity: Int, @SerializedName("temp_kf") val tempKf: Double): Parcelable