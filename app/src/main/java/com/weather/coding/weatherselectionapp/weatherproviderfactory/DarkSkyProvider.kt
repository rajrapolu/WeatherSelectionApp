package com.weather.coding.weatherselectionapp.weatherproviderfactory

import com.weather.coding.weatherselectionapp.dataclasses.CurrentWeatherDTO
import com.weather.coding.weatherselectionapp.util.ModelConversionUtil
import com.weather.coding.weatherselectionapp.dataclasses.DarkSkyDTO
import com.weather.coding.weatherselectionapp.dataclasses.WeatherForecastDTO
import com.weather.coding.weatherselectionapp.networkcalls.NetworkCallListener
import com.weather.coding.weatherselectionapp.networkcalls.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DarkSkyProvider : WeatherProvider() {
    override val supportWeatherForecast: Boolean
        get() = false
    override val displayName: String
        get() = "Dark Sky Api"
    override val baseURL: String
        get() = "https://api.darksky.net/"

    override val apiKey: String
        get() = "0a3f349616016d5a625219e7256d6452"
    override val fieldsRequired: RequiredFields
        get() = RequiredFields.LAT_LNG

    private fun getWeatherServiceEndPoint(latitude: Double, longitude: Double): Call<DarkSkyDTO> {
        return RetrofitService().getWeatherService(baseURL)
                .getDarkSkyData(apiKey, latitude, longitude)
    }

    override fun getWeatherInformation(cityName: String?, countryName: String?, latitude: Double?, longitude: Double?, listener: NetworkCallListener<CurrentWeatherDTO>) {
        if (latitude != null && longitude != null) {
            getWeatherServiceEndPoint(latitude, longitude).enqueue(object : Callback<DarkSkyDTO> {
                override fun onFailure(call: Call<DarkSkyDTO>?, t: Throwable?) {
                    listener.onFailure()
                }

                override fun onResponse(call: Call<DarkSkyDTO>?, response: Response<DarkSkyDTO>?) {
                    if (response != null && response.isSuccessful) {
                        if (response.body() == null) {
                            listener.onSuccess(null)
                        } else {
                            val darkSkyDTO = response.body() as DarkSkyDTO
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

    // Weather Forecast is not implemented for every provider
    override fun getWeatherForecast(cityName: String?, countryName: String?, latitude: Double?,
                                    longitude: Double?, listener: NetworkCallListener<WeatherForecastDTO>) {
    }
}