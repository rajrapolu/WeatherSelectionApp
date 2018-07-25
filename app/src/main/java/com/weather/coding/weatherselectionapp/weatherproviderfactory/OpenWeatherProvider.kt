package com.weather.coding.weatherselectionapp.weatherproviderfactory

import com.weather.coding.weatherselectionapp.CurrentWeatherDTO
import com.weather.coding.weatherselectionapp.RequiredFields
import com.weather.coding.weatherselectionapp.Util.ModelConversionUtil
import com.weather.coding.weatherselectionapp.dataobjects.OpenWeatherModel
import com.weather.coding.weatherselectionapp.networkcalls.NetworkCallListener
import com.weather.coding.weatherselectionapp.networkcalls.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OpenWeatherProvider : WeatherProvider() {
    override val displayName: String
        get() = "Open Weather Api"
    override val apiKey: String
        get() = "f12e77308bfba46a4939b7de916db179"
    override val fieldsRequired: RequiredFields
        get() = RequiredFields.CITY_COUNTRY_NAME
    override val baseURL: String
        get() = "http://api.openweathermap.org/"
    val units = "imperial"

    override fun getWeatherInformation(cityName: String?, countryName: String?, latitude: Double?, longitude: Double?, listener: NetworkCallListener<CurrentWeatherDTO>) {
        if (cityName != null && countryName != null) {
            getWeatherServiceEndPoint(cityName, countryName)
                    .enqueue(object : Callback<OpenWeatherModel.LocationWeatherDTO> {
                        override fun onFailure(call: Call<OpenWeatherModel.LocationWeatherDTO>?, t: Throwable?) {
                            listener.onFailure()
                        }

                        override fun onResponse(call: Call<OpenWeatherModel.LocationWeatherDTO>?, response: Response<OpenWeatherModel.LocationWeatherDTO>?) {
                            if (response != null && response.isSuccessful) {
                                if (response.body() == null) {
                                    listener.onSuccess(null)
                                } else {
                                    val locationWeatherDTO = response.body() as OpenWeatherModel.LocationWeatherDTO
                                    listener.onSuccess(ModelConversionUtil.convertOpenWeatherResponse(locationWeatherDTO))
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

    fun getWeatherServiceEndPoint(cityName: String, countryName: String): Call<OpenWeatherModel.LocationWeatherDTO> {
        return RetrofitService().getWeatherService(baseURL).getOpenWeatherData("$cityName,$countryName", units, apiKey)
    }
}