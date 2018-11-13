package com.weather.coding.weatherselectionapp.weatherproviderfactory

import com.weather.coding.weatherselectionapp.dataclasses.CurrentWeatherDTO
import com.weather.coding.weatherselectionapp.util.ModelConversionUtil
import com.weather.coding.weatherselectionapp.dataclasses.WeatherBitDTO
import com.weather.coding.weatherselectionapp.dataclasses.WeatherBitForecastDTO
import com.weather.coding.weatherselectionapp.dataclasses.WeatherForecastDTO
import com.weather.coding.weatherselectionapp.networkcalls.NetworkCallListener
import com.weather.coding.weatherselectionapp.networkcalls.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherBitProvider : WeatherProvider() {
    override val supportWeatherForecast: Boolean
        get() = true
    override val baseURL: String
        get() = "https://api.weatherbit.io/"
    override val apiKey: String
        get() = "fd2bd93ad0fd45faba9a8f658fab3cdf"
    override val fieldsRequired: RequiredFields
        get() = RequiredFields.CITY_NAME
    override val displayName: String
        get() = "WeatherBit Api"
    val units = "I"

    fun getWeatherServiceEndPoint(cityName: String): Call<WeatherBitDTO> {
        return RetrofitService().getWeatherService(baseURL)
                .getWeatherBitData(cityName, units, apiKey)
    }

    private fun getWeatherForecastEndPointService(cityName: String): Call<WeatherBitForecastDTO> {
        return RetrofitService().getWeatherService(baseURL).getWeatherBitForecast(cityName, units, weatherForecastDays, apiKey)
    }

    override fun getWeatherInformation(cityName: String?, countryName: String?, latitude: Double?, longitude: Double?, listener: NetworkCallListener<CurrentWeatherDTO>) {
        if (cityName != null) {
            getWeatherServiceEndPoint(cityName).enqueue(object : Callback<WeatherBitDTO> {
                override fun onFailure(call: Call<WeatherBitDTO>?, t: Throwable?) {
                    listener.onFailure()
                }

                override fun onResponse(call: Call<WeatherBitDTO>?, response: Response<WeatherBitDTO>?) {
                    if (response != null && response.isSuccessful) {
                        if (response.body() == null) {
                            listener.onSuccess(null)
                        } else {
                            val weatherBitDTO = response.body() as WeatherBitDTO
                            listener.onSuccess(ModelConversionUtil.convertWeatherBitResponse(weatherBitDTO))
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

    override fun getWeatherForecast(cityName: String?, countryName: String?, latitude: Double?, longitude: Double?, listener: NetworkCallListener<WeatherForecastDTO>) {
        if (cityName != null) {
            getWeatherForecastEndPointService(cityName).enqueue(object : Callback<WeatherBitForecastDTO> {
                override fun onFailure(call: Call<WeatherBitForecastDTO>?, t: Throwable?) {
                    listener.onFailure()
                }

                override fun onResponse(call: Call<WeatherBitForecastDTO>?, response: Response<WeatherBitForecastDTO>?) {
                    if (response != null && response.isSuccessful) {
                        if (response.body() == null) {
                            listener.onSuccess(null)
                        } else {
                            val weatherBitForecastDTO = response.body() as WeatherBitForecastDTO
                            listener.onSuccess(ModelConversionUtil.convertWeatherBitToForecast(weatherBitForecastDTO))
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
}