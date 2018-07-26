package com.weather.coding.weatherselectionapp.weatherforecast

import android.arch.core.util.Function
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.content.Context
import com.weather.coding.weatherselectionapp.Util.SharedPreferenceUtil
import com.weather.coding.weatherselectionapp.WeatherForecastModel
import com.weather.coding.weatherselectionapp.networkcalls.WeatherForecastNetworkRequests
import com.weather.coding.weatherselectionapp.weatherproviderfactory.WeatherProvider
import com.weather.coding.weatherselectionapp.weatherproviderfactory.WeatherProviderFactory

class WeatherForecastViewModel : ViewModel() {
    var mWeatherForecastDTO: MutableLiveData<WeatherForecastModel.WeatherForecastDTO>? = null
    var mWeatherForecastRequests = WeatherForecastNetworkRequests()
    var mForecastRequestLiveData: MutableLiveData<WeatherForecastModel.WeatherForecastDTO>? = null
    var mWeatherResponseLiveData = MutableLiveData<WeatherForecastModel.WeatherForecastDTO>()

    fun getWeatherForecastData(): MutableLiveData<WeatherForecastModel.WeatherForecastDTO> {
        mForecastRequestLiveData = mWeatherForecastRequests.getForecastRequestLiveData()
        mWeatherForecastDTO = Transformations.switchMap(mForecastRequestLiveData as MutableLiveData, object : Function<WeatherForecastModel.WeatherForecastDTO, LiveData<WeatherForecastModel.WeatherForecastDTO>> {
            override fun apply(weatherForecast: WeatherForecastModel.WeatherForecastDTO?): LiveData<WeatherForecastModel.WeatherForecastDTO> {
                mWeatherResponseLiveData.value = weatherForecast
                return mWeatherResponseLiveData
            }
        }) as MutableLiveData<WeatherForecastModel.WeatherForecastDTO>
        return mWeatherForecastDTO as MutableLiveData<WeatherForecastModel.WeatherForecastDTO>
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