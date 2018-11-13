package com.weather.coding.weatherselectionapp.dataclasses

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WeatherDTO(val id: Int, val main: String, val description: String, val icon: String): Parcelable