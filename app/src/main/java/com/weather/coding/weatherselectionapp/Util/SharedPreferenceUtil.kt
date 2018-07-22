package com.weather.coding.weatherselectionapp.Util

import android.content.Context
import android.content.SharedPreferences
import java.util.*
import kotlin.collections.LinkedHashSet

private const val SHARED_PREF_NAME = "com.weather.coding.weatherselectionapp.Util.SharedPreferenceUtil"
private const val CITY_NAME = "CITY_NAME_KEY"
private const val COUNTRY_NAME = "COUNTRY_NAME_KEY"
private const val WEATHER_PROVIDER_KEY = "WEATHER_PROVIDER_KEY"

class SharedPreferenceUtil {
    companion object {
        var sharedPreferences: SharedPreferences? = null
        var sharedPreferenceEditor: SharedPreferences.Editor? = null
        var sharedPreferenceUtil: SharedPreferenceUtil? = null

        fun getInstance(context: Context): SharedPreferenceUtil {
            if (sharedPreferences == null) sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            if (sharedPreferenceEditor == null) sharedPreferenceEditor = sharedPreferences?.edit()
            if (sharedPreferenceUtil == null) sharedPreferenceUtil = SharedPreferenceUtil()
            return sharedPreferenceUtil as SharedPreferenceUtil
        }
    }

    fun getKey(attributeKey: String): String {
        return SHARED_PREF_NAME + "_" + attributeKey
    }

    fun saveLocationPref(cityName: String?, countryName: String?) {
        if (cityName != null && countryName != null) {
            sharedPreferenceEditor?.putString(getKey(CITY_NAME), cityName)
            sharedPreferenceEditor?.putString(getKey(COUNTRY_NAME), countryName)
            sharedPreferenceEditor?.apply()
        }
    }

    fun saveWeatherProviderPref(weatherProvider: String) {
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

    fun clearSavedLocation() {
        sharedPreferenceEditor?.remove(getKey(CITY_NAME))
        sharedPreferenceEditor?.remove(getKey(COUNTRY_NAME))
        sharedPreferenceEditor?.apply()
    }

    fun clearSavedWeatherProvider() {
        sharedPreferenceEditor?.remove(getKey(WEATHER_PROVIDER_KEY))
        sharedPreferenceEditor?.apply()
    }
}