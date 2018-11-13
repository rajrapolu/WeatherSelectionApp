package com.weather.coding.weatherselectionapp.currentweather

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import com.weather.coding.weatherselectionapp.ConstantsClass
import com.weather.coding.weatherselectionapp.dataclasses.CurrentWeatherDTO
import com.weather.coding.weatherselectionapp.R
import com.weather.coding.weatherselectionapp.util.UnitsUtil
import com.weather.coding.weatherselectionapp.weatherproviderfactory.WeatherProviders
import com.weather.coding.weatherselectionapp.weatherforecast.WeatherForecastActivity
import kotlinx.android.synthetic.main.activity_current_weather.*

class CurrentWeatherActivity : AppCompatActivity() {

    private lateinit var mCurrentWeatherViewModel: CurrentWeatherViewModel
    private var currentWeather: CurrentWeatherDTO? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_current_weather)
        mCurrentWeatherViewModel = ViewModelProviders.of(this).get(CurrentWeatherViewModel::class.java)
        currentWeather = intent.getParcelableExtra(ConstantsClass.LOCATION_WEATHER_KEY)
        populateUI()
    }

    private fun populateUI() {
        val weatherProvider = mCurrentWeatherViewModel.getWeatherProviderFactoryObject(applicationContext)
        val weatherProviderName = mCurrentWeatherViewModel.getSavedWeatherProvider(applicationContext)
        current_weather_provider.text = weatherProvider?.displayName
        if (currentWeather != null) {
            mCurrentWeatherViewModel.saveCurrentWeather(currentWeather!!)
            current_weather_city_country_label.text = currentWeather!!.location
            current_weather_temp_value.text = getString(R.string.current_temp_label, currentWeather!!.currentTemp, UnitsUtil.getFahrenheitUnit())
            if (currentWeather!!.windSpeed != null) {
                current_weather_wind.visibility = View.VISIBLE
                if (weatherProviderName != WeatherProviders.FIVE_DAY_WEATHER.name) {
                    current_weather_wind.text = getString(R.string.current_weather_wind_label, currentWeather!!.windSpeed, UnitsUtil.getWindSpeedUnits())
                } else {
                    current_weather_wind.text = getString(R.string.current_weather_wind_label_without_units, currentWeather!!.windSpeed)
                }
            }
            if (currentWeather!!.minTemp != null) {
                current_weather_min_temp.visibility = View.VISIBLE
                current_weather_min_temp.text = getString(R.string.current_weather_min_label, currentWeather!!.minTemp, UnitsUtil.getFahrenheitUnit())
            }
            if (currentWeather!!.maxTemp != null) {
                current_weather_max_temp.visibility = View.VISIBLE
                current_weather_max_temp.text = getString(R.string.current_weather_max_label, currentWeather!!.maxTemp, UnitsUtil.getFahrenheitUnit())
            }
        }
        if (weatherProvider != null && weatherProvider.supportWeatherForecast) {
            current_weather_forecast_button.visibility = View.VISIBLE
            current_weather_forecast_button.setOnClickListener { _ -> onWeatherForecastButtonClicked(currentWeather?.location) }
        }
    }

    private fun onWeatherForecastButtonClicked(location: String?) {
        val intent = Intent(this, WeatherForecastActivity::class.java)
        intent.putExtra(ConstantsClass.CITY_NAME_EXTRA, location)
        startActivity(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item != null) {
            val id = item.itemId
            if (id == android.R.id.home) {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
