package com.weather.coding.weatherselectionapp.networkcalls

import android.util.Log
import com.weather.coding.weatherselectionapp.CurrentWeatherDTO
import com.weather.coding.weatherselectionapp.dataobjects.DarkSkyModel
import com.weather.coding.weatherselectionapp.dataobjects.OpenWeatherModel
import com.weather.coding.weatherselectionapp.Util.ModelConversionUtil
import com.weather.coding.weatherselectionapp.WeatherProviders
import com.weather.coding.weatherselectionapp.dataobjects.FiveDayWeatherModel
import com.weather.coding.weatherselectionapp.dataobjects.WeatherBitModel
import com.weather.coding.weatherselectionapp.weatherproviderfactory.WeatherProviderFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NetworkRequests {

    interface NetworkCallListener<T> {
        fun onSuccess(model: T?)
        fun onFailure()
    }

    fun getOpenWeatherInformation(cityName: String, countryName: String, listener: NetworkCallListener<CurrentWeatherDTO>) {
        val weatherProvider = WeatherProviderFactory().getWeatherProvider(WeatherProviders.OPEN_WEATHER.name)
        RetrofitService().getWeatherService(weatherProvider.baseURL)
                ?.getOpenWeatherData("$cityName,$countryName", "imperial", weatherProvider.apiKey)
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
        val weatherProvider = WeatherProviderFactory().getWeatherProvider(WeatherProviders.DARK_SKY.name)
        RetrofitService().getWeatherService(weatherProvider.baseURL)
                ?.getDarkSkyData(weatherProvider.apiKey, latitude, longitude)
                ?.enqueue(object : Callback<DarkSkyModel.DarkSkyDTO> {
                    override fun onFailure(call: Call<DarkSkyModel.DarkSkyDTO>?, t: Throwable?) {

                    }

                    override fun onResponse(call: Call<DarkSkyModel.DarkSkyDTO>?, response: Response<DarkSkyModel.DarkSkyDTO>?) {
                        if (response?.body() == null) {
                            listener.onSuccess(null)
                        } else {
                            val darkSkyDTO = response.body() as DarkSkyModel.DarkSkyDTO
                            listener.onSuccess(ModelConversionUtil.convertDarkSkyDTO(darkSkyDTO))
                        }
                    }
                })
    }

    fun getFiveDayWeatherInformation(cityName: String, listener: NetworkCallListener<CurrentWeatherDTO>) {
        val weatherProvider = WeatherProviderFactory().getWeatherProvider(WeatherProviders.FIVE_DAY_WEATHER.name)
        RetrofitService().getWeatherService(weatherProvider.baseURL)
                ?.getFiveDayWeatherData(cityName)
                ?.enqueue(object : Callback<FiveDayWeatherModel.FiveDayWeatherDataDTO> {
                    override fun onFailure(call: Call<FiveDayWeatherModel.FiveDayWeatherDataDTO>?, t: Throwable?) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onResponse(call: Call<FiveDayWeatherModel.FiveDayWeatherDataDTO>?, response: Response<FiveDayWeatherModel.FiveDayWeatherDataDTO>?) {
                        if (response?.body() == null) {
                            listener.onSuccess(null)
                        } else {
                            val fiveDayWeatherDTO = response.body() as FiveDayWeatherModel.FiveDayWeatherDataDTO
                            listener.onSuccess(ModelConversionUtil.convertFiveDayWeatherDTO(fiveDayWeatherDTO))
                        }
                    }

                })
    }

    fun getWeatherBitInformation(cityName: String, listener: NetworkCallListener<CurrentWeatherDTO>) {
        val weatherProvider = WeatherProviderFactory().getWeatherProvider(WeatherProviders.WEATHER_BIT.name)
        RetrofitService().getWeatherService(weatherProvider.baseURL)
                ?.getWeatherBitData(cityName, "I", weatherProvider.apiKey)
                ?.enqueue(object : Callback<WeatherBitModel.WeatherBitDTO> {
                    override fun onFailure(call: Call<WeatherBitModel.WeatherBitDTO>?, t: Throwable?) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onResponse(call: Call<WeatherBitModel.WeatherBitDTO>?, response: Response<WeatherBitModel.WeatherBitDTO>?) {
                        if (response?.body() == null) {
                            listener.onSuccess(null)
                        } else {
                            val weatherBitDTO = response.body() as WeatherBitModel.WeatherBitDTO
                            listener.onSuccess(ModelConversionUtil.convertWeatherBitResponse(weatherBitDTO))
                        }
                    }

                })
    }
}