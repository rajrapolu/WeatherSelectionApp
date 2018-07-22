package com.weather.coding.weatherselectionapp.locationinput

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.weather.coding.weatherselectionapp.NotificationUtil
import com.weather.coding.weatherselectionapp.OpenWeatherModel
import com.weather.coding.weatherselectionapp.R
import kotlinx.android.synthetic.main.activity_weather_provider.*

class LocationInputActivity : AppCompatActivity() {
    private lateinit var mLocationInputViewModel: LocationInputViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_provider)
        initializeActivity()
    }

    private fun initializeActivity() {
        mLocationInputViewModel = ViewModelProviders.of(this).get(LocationInputViewModel::class.java)

        startObserving()

        createPeriodicWeatherFetchCall()
        NotificationUtil.createNotificationChannel(applicationContext)

        location_continue.setOnClickListener{ _ -> onButtonClicked()}
    }

    private fun createPeriodicWeatherFetchCall() {
        mLocationInputViewModel.createPeriodicFetchCall(applicationContext)
    }

    /**
     * Observes changes to the mLocationInputViewModel
     */
    private fun startObserving() {
        val locationWeatherObserver: Observer<OpenWeatherModel.LocationWeatherDTO>
                = Observer { locationWeather -> populateUI(locationWeather) }
        mLocationInputViewModel.getOpenWeatherData().observe(this, locationWeatherObserver)
    }

    private fun populateUI(locationWeather: OpenWeatherModel.LocationWeatherDTO?) {
        if (locationWeather != null) {
            Toast.makeText(this, locationWeather.city.name, Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Enter valid city", Toast.LENGTH_LONG).show()
        }
    }

    private fun onButtonClicked() {
        mLocationInputViewModel.getOpenWeatherInformation(location_city_name.text.toString(), location_country_name.text.toString())
    }
}
