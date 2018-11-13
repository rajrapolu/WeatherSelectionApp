package com.weather.coding.weatherselectionapp.weatherproviderfactory

import com.weather.coding.weatherselectionapp.CurrentWeatherDTO
import com.weather.coding.weatherselectionapp.RequiredFields
import com.weather.coding.weatherselectionapp.Util.ModelConversionUtil
import com.weather.coding.weatherselectionapp.WeatherForecastModel
import com.weather.coding.weatherselectionapp.dataclasses.LocationWeatherDTO
import com.weather.coding.weatherselectionapp.networkcalls.NetworkCallListener
import com.weather.coding.weatherselectionapp.networkcalls.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OpenWeatherProvider : WeatherProvider() {
    override val supportWeatherForecast: Boolean
        get() = false
    override val displayName: String
        get() = "Open Weather Api"
    override val apiKey: String
        get() = "f12e77308bfba46a4939b7de916db179"
    override val fieldsRequired: RequiredFields
        get() = RequiredFields.CITY_COUNTRY_NAME
    override val baseURL: String
        get() = "http://api.openweathermap.org/"
    val units = "imperial"

    fun getWeatherServiceEndPoint(cityName: String, countryName: String): Call<LocationWeatherDTO> {
        return RetrofitService().getWeatherService(baseURL).getOpenWeatherData("$cityName,$countryName", units, apiKey)
    }

    override fun getWeatherInformation(cityName: String?, countryName: String?, latitude: Double?, longitude: Double?, listener: NetworkCallListener<CurrentWeatherDTO>) {
        if (cityName != null && countryName != null) {
            getWeatherServiceEndPoint(cityName, countryName)
                    .enqueue(object : Callback<LocationWeatherDTO> {
                        override fun onFailure(call: Call<LocationWeatherDTO>?, t: Throwable?) {
                            listener.onFailure()
                        }

                        override fun onResponse(call: Call<LocationWeatherDTO>?, response: Response<LocationWeatherDTO>?) {
                            if (response != null && response.isSuccessful) {
                                if (response.body() == null) {
                                    listener.onSuccess(null)
                                } else {
                                    val locationWeatherDTO = response.body() as LocationWeatherDTO
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

    // Weather Forecast is not implemented for every provider
    override fun getWeatherForecast(cityName: String?, countryName: String?, latitude: Double?,
                                    longitude: Double?, listener: NetworkCallListener<WeatherForecastModel.WeatherForecastDTO>) {
    }
}