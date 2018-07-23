package com.weather.coding.weatherselectionapp.currentweather

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.weather.coding.weatherselectionapp.CurrentWeatherDTO
import com.weather.coding.weatherselectionapp.OpenWeatherModel
import com.weather.coding.weatherselectionapp.R
import com.weather.coding.weatherselectionapp.Util.UnitsUtil
import kotlinx.android.synthetic.main.activity_current_weather.*

private const val LOCATION_WEATHER_KEY: String = "LOCATION_WEATHER_KEY"

class CurrentWeatherActivity : AppCompatActivity() {

    companion object {
        fun newInstance(context: Context, currentWeather: CurrentWeatherDTO) {
            val intent = Intent(context, CurrentWeatherActivity::class.java)
            intent.putExtra(LOCATION_WEATHER_KEY, currentWeather)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_current_weather)
        populateUI()
    }

    private fun populateUI() {
        val currentWeather: CurrentWeatherDTO? = intent.getParcelableExtra(LOCATION_WEATHER_KEY)
        if (currentWeather != null) {
            current_weather_city_country_label.text = currentWeather.location
            current_weather_temp_value.text = getString(R.string.current_temp_label, currentWeather.currentTemp, UnitsUtil.getFahrenheitUnit())
//            current_weather_min_temp.text = getString(R.string.current_weather_min_label, weatherMainDTO.tempMin.toString(), UnitsUtil.getFahrenheitUnit())
//            current_weather_max_temp.text = getString(R.string.current_weather_max_label, weatherMainDTO.tempMax.toString(), UnitsUtil.getFahrenheitUnit())
//            val wind = currentWeather.windSpeed
//            current_weather_wind.text = getString(R.string.current_weather_wind_label, wind.speed.toString(), UnitsUtil.getWindSpeedUnits(), wind.degree.toString(), UnitsUtil.getWindDirectionUnits())
        }
    }


}
