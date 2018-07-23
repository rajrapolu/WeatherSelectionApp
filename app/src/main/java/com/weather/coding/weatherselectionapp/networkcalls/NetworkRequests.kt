package com.weather.coding.weatherselectionapp.networkcalls

import android.util.Log
import com.weather.coding.weatherselectionapp.ApiKeyInfo
import com.weather.coding.weatherselectionapp.CurrentWeatherDTO
import com.weather.coding.weatherselectionapp.DarkSkyModel
import com.weather.coding.weatherselectionapp.OpenWeatherModel
import com.weather.coding.weatherselectionapp.Util.ModelConversionUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NetworkRequests {

    interface NetworkCallListener<T> {
        fun onSuccess(model: T?)
        fun onFailure()
    }

//    fun getOpenWeatherInformation(cityName: String, countryName: String, listener: NetworkCallListener<OpenWeatherModel.LocationWeatherDTO>) {
//        RetrofitService.getRetrofitService()
//                ?.getOpenWeatherData("$cityName,$countryName", "imperial", ApiKeyInfo.OPEN_WEATHER_API_KEY)
//                ?.enqueue(object : Callback<OpenWeatherModel.LocationWeatherDTO> {
//                    override fun onFailure(call: Call<OpenWeatherModel.LocationWeatherDTO>?, t: Throwable?) {
//                        Log.i("verifyingObject", "failed" + t.toString())
//                    }
//
//                    override fun onResponse(call: Call<OpenWeatherModel.LocationWeatherDTO>?, response: Response<OpenWeatherModel.LocationWeatherDTO>?) {
//                        if (response?.body() == null) {
//                            listener.onSuccess(null)
//                        } else {
//                            val locationWeatherDTO = response.body() as OpenWeatherModel.LocationWeatherDTO
//                            listener.onSuccess(locationWeatherDTO)
//                            Log.i("verifyingObject", locationWeatherDTO.coordinates + " " + locationWeatherDTO.city.name)
//                        }
//                    }
//                })
//    }

    fun getOpenWeatherInformation(cityName: String, countryName: String, listener: NetworkCallListener<CurrentWeatherDTO>) {
        RetrofitService().getWeatherService("http://api.openweathermap.org/")
                ?.getOpenWeatherData("$cityName,$countryName", "imperial", ApiKeyInfo.OPEN_WEATHER_API_KEY)
                ?.enqueue(object : Callback<OpenWeatherModel.LocationWeatherDTO> {
                    override fun onFailure(call: Call<OpenWeatherModel.LocationWeatherDTO>?, t: Throwable?) {
                        Log.i("verifyingObject", "failed" + t.toString())
                    }

                    override fun onResponse(call: Call<OpenWeatherModel.LocationWeatherDTO>?, response: Response<OpenWeatherModel.LocationWeatherDTO>?) {
                        if (response?.body() == null) {
                            listener.onSuccess(null)
                        } else {
                            val locationWeatherDTO = response.body() as OpenWeatherModel.LocationWeatherDTO
                            listener.onSuccess(ModelConversionUtil.convertOpenWeatherResponse(locationWeatherDTO))
                            Log.i("verifyingObject", locationWeatherDTO.coordinates + " " + locationWeatherDTO.city.name)
                        }
                    }
                })
    }

    fun getDarkSkyInformation(latitude: Double, longitude: Double, listener: NetworkCallListener<CurrentWeatherDTO>) {
        RetrofitService().getWeatherService("https://api.darksky.net/")
                ?.getDarkSkyData("0a3f349616016d5a625219e7256d6452", latitude, longitude)
                ?.enqueue(object : Callback<DarkSkyModel.DarkSkyDTO> {
                    override fun onFailure(call: Call<DarkSkyModel.DarkSkyDTO>?, t: Throwable?) {

                    }

                    override fun onResponse(call: Call<DarkSkyModel.DarkSkyDTO>?, response: Response<DarkSkyModel.DarkSkyDTO>?) {
                        if (response?.body() == null) {
                            listener.onSuccess(null)
                        } else {
                            val darkSkyDTO = response.body() as DarkSkyModel.DarkSkyDTO
                            listener.onSuccess(ModelConversionUtil.convertDarkSkyDTO(darkSkyDTO))
//                            Log.i("verifyingObject", locationWeatherDTO.coordinates + " " + locationWeatherDTO.city.name)
                        }
                    }

                })
    }
}