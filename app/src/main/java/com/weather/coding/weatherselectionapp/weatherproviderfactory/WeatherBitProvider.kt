package com.weather.coding.weatherselectionapp.weatherproviderfactory

import com.weather.coding.weatherselectionapp.CurrentWeatherDTO
import com.weather.coding.weatherselectionapp.RequiredFields
import com.weather.coding.weatherselectionapp.Util.ModelConversionUtil
import com.weather.coding.weatherselectionapp.WeatherForecastModel
import com.weather.coding.weatherselectionapp.dataobjects.WeatherBitForecastModel
import com.weather.coding.weatherselectionapp.dataobjects.WeatherBitModel
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
    // Add your api key
    override val apiKey: String
        get() = ""
    override val fieldsRequired: RequiredFields
        get() = RequiredFields.CITY_NAME
    override val displayName: String
        get() = "WeatherBit Api"
    val units = "I"

    fun getWeatherServiceEndPoint(cityName: String): Call<WeatherBitModel.WeatherBitDTO> {
        return RetrofitService().getWeatherService(baseURL)
                .getWeatherBitData(cityName, units, apiKey)
    }

    private fun getWeatherForecastEndPointService(cityName: String): Call<WeatherBitForecastModel.WeatherBitForecastDTO> {
        return RetrofitService().getWeatherService(baseURL).getWeatherBitForecast(cityName, units, weatherForecastDays, apiKey)
    }

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

    override fun getWeatherForecast(cityName: String?, countryName: String?, latitude: Double?, longitude: Double?, listener: NetworkCallListener<WeatherForecastModel.WeatherForecastDTO>) {
        if (cityName != null) {
            getWeatherForecastEndPointService(cityName).enqueue(object : Callback<WeatherBitForecastModel.WeatherBitForecastDTO> {
                override fun onFailure(call: Call<WeatherBitForecastModel.WeatherBitForecastDTO>?, t: Throwable?) {
                    listener.onFailure()
                }

                override fun onResponse(call: Call<WeatherBitForecastModel.WeatherBitForecastDTO>?, response: Response<WeatherBitForecastModel.WeatherBitForecastDTO>?) {
                    if (response != null && response.isSuccessful) {
                        if (response.body() == null) {
                            listener.onSuccess(null)
                        } else {
                            val weatherBitForecastDTO = response.body() as WeatherBitForecastModel.WeatherBitForecastDTO
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