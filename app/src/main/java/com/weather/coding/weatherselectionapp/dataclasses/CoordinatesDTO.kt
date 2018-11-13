package com.weather.coding.weatherselectionapp.dataclasses

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CoordinatesDTO(val lat: Double, val lon: Double): Parcelable