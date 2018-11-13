package com.weather.coding.weatherselectionapp.weatherforecast

import android.arch.core.util.Function
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.content.Context
import com.weather.coding.weatherselectionapp.util.SharedPreferenceUtil
import com.weather.coding.weatherselectionapp.dataclasses.WeatherForecastDTO
import com.weather.coding.weatherselectionapp.networkcalls.WeatherForecastNetworkRequests
import com.weather.coding.weatherselectionapp.weatherproviderfactory.WeatherProvider
import com.weather.coding.weatherselectionapp.weatherproviderfactory.WeatherProviderFactory

class WeatherForecastViewModel : ViewModel() {
    var mWeatherForecastDTO: MutableLiveData<WeatherForecastDTO>? = null
    var mWeatherForecastRequests = WeatherForecastNetworkRequests()
    var mForecastRequestLiveData: MutableLiveData<WeatherForecastDTO>? = null
    var mWeatherResponseLiveData = MutableLiveData<WeatherForecastDTO>()

    // This method observes on the changes on network request and notifies the activity when data is available
    fun getWeatherForecastData(): MutableLiveData<WeatherForecastDTO> {
        mForecastRequestLiveData = mWeatherForecastRequests.getForecastRequestLiveData()
        mWeatherForecastDTO = Transformations.switchMap(mForecastRequestLiveData as MutableLiveData, object : Function<WeatherForecastDTO, LiveData<WeatherForecastDTO>> {
            override fun apply(weatherForecast: WeatherForecastDTO?): LiveData<WeatherForecastDTO> {
                mWeatherResponseLiveData.value = weatherForecast
                return mWeatherResponseLiveData
            }
        }) as MutableLiveData<WeatherForecastDTO>
        return mWeatherForecastDTO as MutableLiveData<WeatherForecastDTO>
    }


    fun getSavedWeatherProvider(applicationContext: Context): String? {
        return SharedPreferenceUtil.getInstance(applicationContext).getSavedWeatherProvider()
    }

    fun getWeatherProviderObject(weatherProviderName: String): WeatherProvider {
        return WeatherProviderFactory().getWeatherProvider(weatherProviderName)
    }

    fun getWeatherForecast(weatherProvider: WeatherProvider, cityName: String, countryName: String?, latitude: Double?, longitude: Double?) {
        mWeatherForecastRequests.getWeatherForecast(weatherProvider, cityName, countryName, latitude, longitude)
    }
}