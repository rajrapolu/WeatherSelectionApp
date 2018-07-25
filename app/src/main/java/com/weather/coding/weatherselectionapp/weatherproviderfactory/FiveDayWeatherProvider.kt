package com.weather.coding.weatherselectionapp.weatherproviderfactory

import com.weather.coding.weatherselectionapp.CurrentWeatherDTO
import com.weather.coding.weatherselectionapp.RequiredFields
import com.weather.coding.weatherselectionapp.Util.ModelConversionUtil
import com.weather.coding.weatherselectionapp.dataobjects.FiveDayWeatherModel
import com.weather.coding.weatherselectionapp.networkcalls.NetworkCallListener
import com.weather.coding.weatherselectionapp.networkcalls.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FiveDayWeatherProvider : WeatherProvider() {
    override val displayName: String
        get() = "Five Day Weather Api"
    override val baseURL: String
        get() = "https://5dayweather.org/"
    override val apiKey: String
        get() = ""
    override val fieldsRequired: RequiredFields
        get() = RequiredFields.CITY_NAME

    override fun getWeatherInformation(cityName: String?, countryName: String?, latitude: Double?, longitude: Double?, listener: NetworkCallListener<CurrentWeatherDTO>) {
        if (cityName != null) {
            RetrofitService().getWeatherService(baseURL)
                    ?.getFiveDayWeatherData(cityName)
                    ?.enqueue(object : Callback<FiveDayWeatherModel.FiveDayWeatherDataDTO> {
                        override fun onFailure(call: Call<FiveDayWeatherModel.FiveDayWeatherDataDTO>?, t: Throwable?) {
                            listener.onFailure()
                        }

                        override fun onResponse(call: Call<FiveDayWeatherModel.FiveDayWeatherDataDTO>?, response: Response<FiveDayWeatherModel.FiveDayWeatherDataDTO>?) {
                            if (response != null && response.isSuccessful) {
                                if (response.body() == null) {
                                    listener.onSuccess(null)
                                } else {
                                    val fiveDayWeatherDTO = response.body() as FiveDayWeatherModel.FiveDayWeatherDataDTO
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
}