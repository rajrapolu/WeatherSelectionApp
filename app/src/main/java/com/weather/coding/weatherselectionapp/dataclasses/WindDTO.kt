package com.weather.coding.weatherselectionapp.dataclasses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WindDTO(var speed: Double, @SerializedName("deg") var degree: Double): Parcelable