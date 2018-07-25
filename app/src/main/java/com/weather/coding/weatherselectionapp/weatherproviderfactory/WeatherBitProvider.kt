package com.weather.coding.weatherselectionapp.weatherproviderfactory

import com.weather.coding.weatherselectionapp.CurrentWeatherDTO
import com.weather.coding.weatherselectionapp.RequiredFields
import com.weather.coding.weatherselectionapp.Util.ModelConversionUtil
import com.weather.coding.weatherselectionapp.dataobjects.WeatherBitModel
import com.weather.coding.weatherselectionapp.networkcalls.NetworkCallListener
import com.weather.coding.weatherselectionapp.networkcalls.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherBitProvider : WeatherProvider() {
    override val baseURL: String
        get() = "https://api.weatherbit.io/"
    override val apiKey: String
        get() = "fd2bd93ad0fd45faba9a8f658fab3cdf"
    override val fieldsRequired: RequiredFields
        get() = RequiredFields.CITY_NAME
    override val displayName: String
        get() = "WeatherBit Api"
    val units = "I"

    override fun getWeatherInformation(cityName: String?, countryName: String?, latitude: Double?, longitude: Double?, listener: NetworkCallListener<CurrentWeatherDTO>) {
        if (cityName != null) {
            getWeatherServiceEndPoint(cityName).enqueue(object : Callback<WeatherBitModel.WeatherBitDTO> {
                override fun onFailure(call: Call<WeatherBitModel.WeatherBitDTO>?, t: Throwable?) {
                    listener.onFailure()
                }

                override fun onResponse(call: Call<WeatherBitModel.WeatherBitDTO>?, response: Response<WeatherBitModel.WeatherBitDTO>?) {
                    if (response != null && response.isSuccessful) {
                        if (response.body() == null) {
                            listener.onSuccess(null)
                        } else {
                            val weatherBitDTO = response.body() as WeatherBitModel.WeatherBitDTO
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

    fun getWeatherServiceEndPoint(cityName: String): Call<WeatherBitModel.WeatherBitDTO> {
        return RetrofitService().getWeatherService(baseURL)
                .getWeatherBitData(cityName, units, apiKey)
    }
}