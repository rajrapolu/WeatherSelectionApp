package com.weather.coding.weatherselectionapp.weatherprovider

import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.weather.coding.weatherselectionapp.R
import com.weather.coding.weatherselectionapp.WeatherProviders
import com.weather.coding.weatherselectionapp.locationinput.LocationInputActivity
import kotlinx.android.synthetic.main.activity_weather_provider_selection.*

class WeatherProviderSelectionActivity : AppCompatActivity() {

    var mWeatherProvider: String? = null
    lateinit var mWeatherViewModel: WeatherProviderSelectionViewModel

    val onRadioButtonClickListener = View.OnClickListener { view -> onRadioButtonClicked(view) }

    private fun onRadioButtonClicked(view: View) {
        if ((view as RadioButton).isChecked) mWeatherProvider = view.tag as String?
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_provider_selection)

        mWeatherViewModel = ViewModelProviders.of(this).get(WeatherProviderSelectionViewModel::class.java)
        createViewsForActivity()
    }

    /**
     * Create the radio group for weather provider and inflates button layout
     */
    private fun createViewsForActivity() {
        val radioGroup = RadioGroup(this)
        for (weatherProviderKey in WeatherProviders.values()) {
            val weatherProvider = mWeatherViewModel.getWeatherProvider(weatherProviderKey.name)
            val radioButton = RadioButton(this)
            radioButton.tag = weatherProviderKey.name
            radioButton.text = weatherProvider.displayName
            radioButton.setOnClickListener(onRadioButtonClickListener)
            radioGroup.addView(radioButton)
        }
        weather_provider_root_view.addView(radioGroup)
        LayoutInflater.from(this).inflate(R.layout.weather_provider_continue_button_layout, weather_provider_root_view, true)
    }

    /**
     * Callback when continue button is clicked
     */
    fun onWeatherProviderSelected(view: View) {
        if (mWeatherProvider != null) {
            Toast.makeText(this, mWeatherProvider, Toast.LENGTH_LONG).show()
            mWeatherViewModel.saveWeatherProviderPref(applicationContext, mWeatherProvider!!)
            LocationInputActivity.newInstance(this)
        } else {
            Toast.makeText(this, "Please select one of the provider", Toast.LENGTH_LONG).show()
        }
    }
}
