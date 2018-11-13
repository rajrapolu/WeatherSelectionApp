package com.weather.coding.weatherselectionapp.dataclasses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CityDTO(val id: Int, val name: String, @SerializedName("coord") val coordinates: CoordinatesDTO,
                   val country: String, val population: Int) : Parcelable