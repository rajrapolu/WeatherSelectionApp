package com.weather.coding.weatherselectionapp.locationinput

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.places.Places
import com.google.android.gms.location.places.ui.PlacePicker
import com.weather.coding.weatherselectionapp.CurrentWeatherDTO
import com.weather.coding.weatherselectionapp.R
import com.weather.coding.weatherselectionapp.Util.NotificationUtil
import com.weather.coding.weatherselectionapp.Util.SharedPreferenceUtil
import com.weather.coding.weatherselectionapp.currentweather.CurrentWeatherActivity
import com.weather.coding.weatherselectionapp.weatherprovider.WeatherProviderSelectionActivity
import kotlinx.android.synthetic.main.activity_location_input.*

private const val PLACE_PICKER_REQUEST: Int = 99

class LocationInputActivity : AppCompatActivity(), GoogleApiClient.OnConnectionFailedListener {

    companion object {
        fun newInstance(context: Context) {
            val intent = Intent(context, LocationInputActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        Toast.makeText(this, "Connection to Google Play Service failed", Toast.LENGTH_LONG).show()
    }

    private lateinit var mLocationInputViewModel: LocationInputViewModel
    private var weatherProvider: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_input)

        setUpGoogleClient()
        initializeActivity()
    }

    private fun initializeActivity() {
        mLocationInputViewModel = ViewModelProviders.of(this).get(LocationInputViewModel::class.java)
        startObserving()
        NotificationUtil.createNotificationChannel(applicationContext)

        configureWeatherProvider()

        createPeriodicWeatherFetchCall()

        location_continue.setOnClickListener { _ -> onButtonClicked() }
    }

    private fun configureWeatherProvider() {
        weatherProvider = SharedPreferenceUtil.getInstance(applicationContext).getSavedWeatherProvider()
        if (weatherProvider != null) {
            if (weatherProvider.equals(WeatherProviderSelectionActivity.WeatherProvider.DARK_SKY.name)) {
                setUpDarkSkyUI()
            } else if (weatherProvider.equals(WeatherProviderSelectionActivity.WeatherProvider.OPEN_WEATHER.name)) {
                setUpOpenWeatherUI()
            }
        }
    }

    private fun setUpDarkSkyUI() {
        location_continue.text = "Select place"
        location_continue.visibility = View.VISIBLE
        location_city_name.visibility = View.GONE
        location_country_name.visibility = View.GONE
    }

    private fun setUpOpenWeatherUI() {
        location_continue.visibility = View.VISIBLE
        location_city_name.visibility = View.VISIBLE
        location_country_name.visibility = View.VISIBLE
    }

    private fun openPlacePicker() {
        val placePickerBuilder = PlacePicker.IntentBuilder()
        startActivityForResult(placePickerBuilder.build(this), PLACE_PICKER_REQUEST)
    }

    private fun setUpGoogleClient() {
        GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Places.GEO_DATA_API)
                .build()

    }

    private fun createPeriodicWeatherFetchCall() {
        mLocationInputViewModel.createPeriodicFetchCall(applicationContext)
    }

    /**
     * Observes changes to the mLocationInputViewModel
     */
    private fun startObserving() {
        val locationWeatherObserver: Observer<CurrentWeatherDTO> = Observer { locationWeather -> populateUI(locationWeather) }
        mLocationInputViewModel.getCurrentWeatherData().observe(this, locationWeatherObserver)
    }

    private fun populateUI(currentWeather: CurrentWeatherDTO?) {
        location_input_progress_bar.visibility = View.GONE
        if (currentWeather != null) {
            CurrentWeatherActivity.newInstance(this, currentWeather)
        } else {
            Toast.makeText(this, "Enter valid city", Toast.LENGTH_LONG).show()
        }
    }

    private fun onButtonClicked() {
        if (weatherProvider.equals(WeatherProviderSelectionActivity.WeatherProvider.DARK_SKY.name)) {
            openPlacePicker()
        } else {
            location_input_progress_bar.visibility = View.VISIBLE
            val cityName = location_city_name?.text?.toString()
            val countryName = location_country_name?.text?.toString()
            if (cityName != null && countryName != null) {
                val sharedPreferenceUtil = SharedPreferenceUtil.getInstance(applicationContext)
                sharedPreferenceUtil.saveLocationPref(cityName, countryName)
                mLocationInputViewModel.getOpenWeatherInformation(location_city_name.text.toString(), location_country_name.text.toString())
            } else {
                Toast.makeText(this, "Please enter valid city and country name", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PLACE_PICKER_REQUEST && resultCode == RESULT_OK) {
            val place = PlacePicker.getPlace(this, data)
            mLocationInputViewModel.getDarkSkyInformation(place.latLng.latitude, place.latLng.longitude)
            Toast.makeText(this, place.latLng.latitude.toString() + " " + place.latLng.longitude.toString(), Toast.LENGTH_LONG).show()
        }
    }
}
