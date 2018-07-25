package com.weather.coding.weatherselectionapp.weatherproviderfactory

import com.weather.coding.weatherselectionapp.CurrentWeatherDTO
import com.weather.coding.weatherselectionapp.RequiredFields
import com.weather.coding.weatherselectionapp.Util.ModelConversionUtil
import com.weather.coding.weatherselectionapp.dataobjects.DarkSkyModel
import com.weather.coding.weatherselectionapp.networkcalls.NetworkCallListener
import com.weather.coding.weatherselectionapp.networkcalls.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DarkSkyProvider : WeatherProvider() {
    override val displayName: String
        get() = "Dark Sky Api"
    override val baseURL: String
        get() = "https://api.darksky.net/"
    override val apiKey: String
        get() = "0a3f349616016d5a625219e7256d6452"
    override val fieldsRequired: RequiredFields
        get() = RequiredFields.LAT_LNG

    override fun getWeatherInformation(cityName: String?, countryName: String?, latitude: Double?, longitude: Double?, listener: NetworkCallListener<CurrentWeatherDTO>) {
        if (latitude != null && longitude != null) {
            getWeatherServiceEndPoint(latitude, longitude).enqueue(object : Callback<DarkSkyModel.DarkSkyDTO> {
                override fun onFailure(call: Call<DarkSkyModel.DarkSkyDTO>?, t: Throwable?) {
                    listener.onFailure()
                }

                override fun onResponse(call: Call<DarkSkyModel.DarkSkyDTO>?, response: Response<DarkSkyModel.DarkSkyDTO>?) {
                    if (response != null && response.isSuccessful) {
                        if (response.body() == null) {
                            listener.onSuccess(null)
                        } else {
                            val darkSkyDTO = response.body() as DarkSkyModel.DarkSkyDTO
                            listener.onSuccess(ModelConversionUtil.convertDarkSkyDTO(darkSkyDTO))
                        }
                    } else {
                        listener.onFailure()
                    }
                }
            })
        } else {
            listener.onFailure()
        }
    }

    fun getWeatherServiceEndPoint(latitude: Double, longitude: Double): Call<DarkSkyModel.DarkSkyDTO> {
        return RetrofitService().getWeatherService(baseURL)
                .getDarkSkyData(apiKey, latitude, longitude)
    }
}