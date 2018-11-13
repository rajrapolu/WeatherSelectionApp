package com.weather.coding.weatherselectionapp.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

private const val SHARED_PREF_NAME = "com.weather.coding.weatherselectionapp.Util.SharedPreferenceUtil"
private const val CITY_NAME = "CITY_NAME_KEY"
private const val COUNTRY_NAME = "COUNTRY_NAME_KEY"
private const val LAT_KEY = "LAT_KEY"
private const val LNG_KEY = "LNG_KEY"
private const val WEATHER_PROVIDER_KEY = "WEATHER_PROVIDER_KEY"

class SharedPreferenceUtil {
    companion object {
        var sharedPreferences: SharedPreferences? = null
        var sharedPreferenceEditor: SharedPreferences.Editor? = null
        var sharedPreferenceUtil: SharedPreferenceUtil? = null

        @SuppressLint("CommitPrefEdits")
        fun getInstance(context: Context): SharedPreferenceUtil {
            if (sharedPreferences == null) sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            if (sharedPreferenceEditor == null) sharedPreferenceEditor = sharedPreferences?.edit()
            if (sharedPreferenceUtil == null) sharedPreferenceUtil = SharedPreferenceUtil()
            return sharedPreferenceUtil as SharedPreferenceUtil
        }
    }

    private fun getKey(attributeKey: String): String {
        return SHARED_PREF_NAME + "_" + attributeKey
    }

    fun saveLocationPref(cityName: String?, countryName: String?) {
        if (cityName != null && countryName != null) {
            sharedPreferenceEditor?.putString(getKey(CITY_NAME), cityName)
            sharedPreferenceEditor?.putString(getKey(COUNTRY_NAME), countryName)
            sharedPreferenceEditor?.apply()
        }
    }

    fun saveLocationLatLngPref(latitude: Double?, longitude: Double?) {
        if (latitude != null && longitude != null) {
            sharedPreferenceEditor?.putFloat(getKey(LAT_KEY), latitude.toFloat())
            sharedPreferenceEditor?.putFloat(getKey(LNG_KEY), longitude.toFloat())
            sharedPreferenceEditor?.apply()
        }
    }

    fun saveWeatherProviderPref(weatherProvider: String?) {
        sharedPreferenceEditor?.putString(getKey(WEATHER_PROVIDER_KEY), weatherProvider)
        sharedPreferenceEditor?.apply()
    }

    fun getSavedCityName(): String? {
        return sharedPreferences?.getString(getKey(CITY_NAME), null)
    }

    fun getSavedCountryName(): String? {
        return sharedPreferences?.getString(getKey(COUNTRY_NAME), null)
    }

    fun getSavedWeatherProvider(): String? {
        return sharedPreferences?.getString(getKey(WEATHER_PROVIDER_KEY), null)
    }

    fun getSavedLatitude(): Double? {
        val latValue = sharedPreferences?.getFloat(getKey(LAT_KEY), 0F)
        if (latValue == 0F) {
            return null
        }
        return latValue?.toDouble()
    }

    fun getSavedLongitude(): Double? {
        val lngValue = sharedPreferences?.getFloat(getKey(LNG_KEY), 0F)
        if (lngValue == 0F) {
            return null
        }
        return lngValue?.toDouble()
    }
}