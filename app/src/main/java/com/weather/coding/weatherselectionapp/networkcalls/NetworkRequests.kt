package com.weather.coding.weatherselectionapp.networkcalls

import android.util.Log
import com.weather.coding.weatherselectionapp.ApiKeyInfo
import com.weather.coding.weatherselectionapp.OpenWeatherModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NetworkRequests {

    interface NetworkCallListener<T> {
        fun onSuccess(model: T?)
        fun onFailure()
    }

    fun getOpenWeatherInformation(cityName: String, countryName: String, listener: NetworkCallListener<OpenWeatherModel.LocationWeatherDTO>) {
        RetrofitService.getRetrofitService()
                ?.getOpenWeatherData("$cityName,$countryName", ApiKeyInfo.OPEN_WEATHER_API_KEY)
                ?.enqueue(object : Callback<OpenWeatherModel.LocationWeatherDTO> {
                    override fun onFailure(call: Call<OpenWeatherModel.LocationWeatherDTO>?, t: Throwable?) {
                        Log.i("verifyingObject", "failed" + t.toString())
                    }

                    override fun onResponse(call: Call<OpenWeatherModel.LocationWeatherDTO>?, response: Response<OpenWeatherModel.LocationWeatherDTO>?) {
                        if (response?.body() == null) {
                            listener.onSuccess(null)
                        } else {
                            val locationWeatherDTO = response.body() as OpenWeatherModel.LocationWeatherDTO
                            listener.onSuccess(locationWeatherDTO)
                            Log.i("verifyingObject", locationWeatherDTO.coordinates + " " + locationWeatherDTO.city.name)
                        }
                    }
                })
    }
}