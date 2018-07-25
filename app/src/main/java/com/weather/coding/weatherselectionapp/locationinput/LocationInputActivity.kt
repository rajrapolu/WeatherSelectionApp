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
import com.weather.coding.weatherselectionapp.RequiredFields
import com.weather.coding.weatherselectionapp.Util.NotificationUtil
import com.weather.coding.weatherselectionapp.Util.SharedPreferenceUtil
import com.weather.coding.weatherselectionapp.WeatherProviders
import com.weather.coding.weatherselectionapp.currentweather.CurrentWeatherActivity
import com.weather.coding.weatherselectionapp.weatherproviderfactory.WeatherProviderFactory
import kotlinx.android.synthetic.main.activity_location_input.*

private const val PLACE_PICKER_REQUEST: Int = 99

class LocationInputActivity : AppCompatActivity(), GoogleApiClient.OnConnectionFailedListener {
    private lateinit var mLocationInputViewModel: LocationInputViewModel
    private var weatherProviderName: String? = null
    private var typeOfUI: RequiredFields? = null

    companion object {
        fun newInstance(context: Context) {
            val intent = Intent(context, LocationInputActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        Toast.makeText(this, getString(R.string.google_play_connection_failed), Toast.LENGTH_LONG).show()
    }

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

        configureWeatherProviderUI()
        location_continue.setOnClickListener { _ -> onButtonClicked() }
    }

    private fun configureWeatherProviderUI() {
        weatherProviderName = mLocationInputViewModel.fetchWeatherProvider(applicationContext)
        if (weatherProviderName != null) {
            val weatherProvider = WeatherProviderFactory().getWeatherProvider(weatherProviderName!!)
            typeOfUI = weatherProvider.fieldsRequired
            configureUIBasedOnRequiredField()
        }
    }

    private fun configureUIBasedOnRequiredField() {
        if (typeOfUI == RequiredFields.CITY_COUNTRY_NAME) {
            location_city_name.visibility = View.VISIBLE
            location_country_name.visibility = View.VISIBLE
        } else if (typeOfUI == RequiredFields.CITY_NAME) {
            location_city_name.visibility = View.VISIBLE
            location_country_name.visibility = View.GONE
        } else if (typeOfUI == RequiredFields.LAT_LNG) {
            location_city_name.visibility = View.GONE
            location_country_name.visibility = View.GONE
            location_continue.text = getString(R.string.lat_lng_provider_button_label)
        }
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
            Toast.makeText(this, getString(R.string.enter_valid_location_label), Toast.LENGTH_LONG).show()
        }
    }

    private fun onButtonClicked() {
        if (typeOfUI != null) {
            if (typeOfUI == RequiredFields.LAT_LNG) {
                openPlacePicker()
            } else {
                mLocationInputViewModel.saveLocationData(applicationContext, location_city_name?.text?.toString(), location_country_name?.text?.toString())
                getWeatherInformation(location_city_name?.text?.toString(), location_country_name?.text?.toString(), null, null)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PLACE_PICKER_REQUEST && resultCode == RESULT_OK) {
            val place = PlacePicker.getPlace(this, data)
            val latitude = place.latLng.latitude
            val longitude = place.latLng.longitude
            mLocationInputViewModel.saveLatLngData(applicationContext, latitude, longitude)
            getWeatherInformation(null, null, latitude, longitude)
            Toast.makeText(this, place.latLng.latitude.toString() + " " + place.latLng.longitude.toString(), Toast.LENGTH_LONG).show()
        }
    }

    private fun getWeatherInformation(cityName: String?, countryName: String?, latitude: Double?, longitude: Double?) {
        location_input_progress_bar.visibility = View.VISIBLE
        if (!mLocationInputViewModel.getWeatherInformation(applicationContext, cityName, countryName, latitude, longitude)) {
            Toast.makeText(this, getString(R.string.something_went_wrong), Toast.LENGTH_LONG).show()
        }
    }
}
