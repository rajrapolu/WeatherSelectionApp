package com.weather.coding.weatherselectionapp.currentweather

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.weather.coding.weatherselectionapp.ConstantsClass
import com.weather.coding.weatherselectionapp.CurrentWeatherDTO
import com.weather.coding.weatherselectionapp.R
import com.weather.coding.weatherselectionapp.Util.UnitsUtil
import kotlinx.android.synthetic.main.activity_current_weather.*

class CurrentWeatherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_current_weather)
        populateUI()
    }

    private fun populateUI() {
        val currentWeather: CurrentWeatherDTO? = intent.getParcelableExtra(ConstantsClass.LOCATION_WEATHER_KEY)
        current_weather_provider.text = ViewModelProviders.of(this).get(CurrentWeatherViewModel::class.java).getSavedWeatherProvider(applicationContext)
        if (currentWeather != null) {
            current_weather_city_country_label.text = currentWeather.location
            current_weather_temp_value.text = getString(R.string.current_temp_label, currentWeather.currentTemp, UnitsUtil.getFahrenheitUnit())
            if (currentWeather.windSpeed != null) {
                current_weather_wind.visibility = View.VISIBLE
                current_weather_wind.text = getString(R.string.current_weather_wind_label, currentWeather.windSpeed, UnitsUtil.getWindSpeedUnits())
            }
            if (currentWeather.minTemp != null) {
                current_weather_min_temp.visibility = View.VISIBLE
                current_weather_min_temp.text = getString(R.string.current_weather_min_label, currentWeather.minTemp, UnitsUtil.getFahrenheitUnit())
            }
            if (currentWeather.maxTemp != null) {
                current_weather_max_temp.visibility = View.VISIBLE
                current_weather_max_temp.text = getString(R.string.current_weather_max_label, currentWeather.maxTemp, UnitsUtil.getFahrenheitUnit())
            }
        }
    }
}
