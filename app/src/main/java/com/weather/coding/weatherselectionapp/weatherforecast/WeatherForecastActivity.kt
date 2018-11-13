package com.weather.coding.weatherselectionapp.weatherforecast

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.weather.coding.weatherselectionapp.ConstantsClass
import com.weather.coding.weatherselectionapp.R
import com.weather.coding.weatherselectionapp.WeatherForecastModel
import com.weather.coding.weatherselectionapp.weatherproviderfactory.WeatherProvider
import kotlinx.android.synthetic.main.activity_weather_forecast.*

class WeatherForecastActivity : AppCompatActivity() {
    private lateinit var mWeatherForecastViewModel: WeatherForecastViewModel
    private var cityName: String? = null
    private lateinit var mWeatherForecastAdapter: WeatherForecastAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_forecast)

        setUpRecyclerView()
        callWeatherForecast()
    }

    private fun setUpRecyclerView() {
        mWeatherForecastAdapter = WeatherForecastAdapter(null)
        weather_forecast_recycler_view.adapter = mWeatherForecastAdapter
        weather_forecast_recycler_view.addItemDecoration(DividerItemDecoration(this, RecyclerView.VERTICAL))
    }

    /**
     * Calls the weather forecast end point
     */
    private fun callWeatherForecast() {
        cityName = intent.getStringExtra(ConstantsClass.CITY_NAME_EXTRA)
        mWeatherForecastViewModel = ViewModelProviders.of(this).get(WeatherForecastViewModel::class.java)
        startObserving()
        val weatherProviderName: String? = mWeatherForecastViewModel.getSavedWeatherProvider(applicationContext)
        if (weatherProviderName != null) {
            val weatherProvider = mWeatherForecastViewModel.getWeatherProviderObject(weatherProviderName)
            if (weatherProvider.supportWeatherForecast) {
                getWeatherForecast(weatherProvider)
            }
        } else {
            Toast.makeText(this, getString(R.string.something_went_wrong), Toast.LENGTH_LONG).show()
        }
    }

    private fun startObserving() {
        val forecastObserver: Observer<WeatherForecastModel.WeatherForecastDTO> = Observer { weatherForecast -> populateUI(weatherForecast) }
        mWeatherForecastViewModel.getWeatherForecastData().observe(this, forecastObserver)
    }

    private fun populateUI(weatherForecast: WeatherForecastModel.WeatherForecastDTO?) {
        weather_forecast_progress_bar.visibility = View.GONE
        weather_forecast_city_name.text = cityName
        mWeatherForecastAdapter.updateRecords(weatherForecast?.dayForecasts)
    }

    private fun getWeatherForecast(weatherProvider: WeatherProvider) {
        if (cityName != null) {
            weather_forecast_progress_bar.visibility = View.VISIBLE
            mWeatherForecastViewModel.getWeatherForecast(weatherProvider, cityName!!, null, null, null)
        }
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
