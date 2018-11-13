package com.weather.coding.weatherselectionapp.weatherproviderfactory

import com.weather.coding.weatherselectionapp.CurrentWeatherDTO
import com.weather.coding.weatherselectionapp.RequiredFields
import com.weather.coding.weatherselectionapp.Util.ModelConversionUtil
import com.weather.coding.weatherselectionapp.WeatherForecastModel
import com.weather.coding.weatherselectionapp.dataclasses.FiveDayWeatherDataDTO
import com.weather.coding.weatherselectionapp.networkcalls.NetworkCallListener
import com.weather.coding.weatherselectionapp.networkcalls.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FiveDayWeatherProvider : WeatherProvider() {
    override val supportWeatherForecast: Boolean
        get() = false
    override val displayName: String
        get() = "Five Day Weather Api"
    override val baseURL: String
        get() = "https://5dayweather.org/"
    override val apiKey: String
        get() = ""
    override val fieldsRequired: RequiredFields
        get() = RequiredFields.CITY_NAME

    fun getWeatherServiceEndPoint(cityName: String): Call<FiveDayWeatherDataDTO>
    {
        return RetrofitService().getWeatherService(baseURL)
                .getFiveDayWeatherData(cityName)
    }

    override fun getWeatherInformation(cityName: String?, countryName: String?, latitude: Double?, longitude: Double?, listener: NetworkCallListener<CurrentWeatherDTO>) {
        if (cityName != null) {
            getWeatherServiceEndPoint(cityName).enqueue(object : Callback<FiveDayWeatherDataDTO> {
                override fun onFailure(call: Call<FiveDayWeatherDataDTO>?, t: Throwable?) {
                    listener.onFailure()
                }

                override fun onResponse(call: Call<FiveDayWeatherDataDTO>?, response: Response<FiveDayWeatherDataDTO>?) {
                    if (response != null && response.isSuccessful) {
                        if (response.body() == null) {
                            listener.onSuccess(null)
                        } else {
                            val fiveDayWeatherDTO = response.body() as FiveDayWeatherDataDTO
                            listener.onSuccess(ModelConversionUtil.convertFiveDayWeatherDTO(fiveDayWeatherDTO))
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