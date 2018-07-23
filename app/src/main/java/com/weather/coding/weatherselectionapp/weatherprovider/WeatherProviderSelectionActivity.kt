package com.weather.coding.weatherselectionapp.weatherprovider

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import com.weather.coding.weatherselectionapp.R
import com.weather.coding.weatherselectionapp.Util.SharedPreferenceUtil
import com.weather.coding.weatherselectionapp.locationinput.LocationInputActivity
import kotlinx.android.synthetic.main.activity_weather_provider_selection.*

class WeatherProviderSelectionActivity : AppCompatActivity() {

    var mWeatherProvider: String? = null

    enum class WeatherProvider {
        OPEN_WEATHER, DARK_SKY
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_provider_selection)

        initializeActivity()
    }

    private fun initializeActivity() {
        weather_provider_continue_button.setOnClickListener { view -> onWeatherProviderSelected() }
    }

    private fun onWeatherProviderSelected() {
        if (mWeatherProvider != null) {
            Toast.makeText(this, mWeatherProvider, Toast.LENGTH_LONG).show()
            SharedPreferenceUtil.getInstance(applicationContext).saveWeatherProviderPref(mWeatherProvider)
            LocationInputActivity.newInstance(this)
        } else {
            Toast.makeText(this, "Please select one of the provider", Toast.LENGTH_LONG).show()
        }
    }

    fun onRadioButtonsClicked(view: View) {
        val radioButtonChecked: Boolean = (view as RadioButton).isChecked
        when (view.id) {
            R.id.weather_provider_open_weather -> {
                if (radioButtonChecked) mWeatherProvider = WeatherProvider.OPEN_WEATHER.name
            }
            R.id.weather_provider_dark_sky -> {
                if (radioButtonChecked) mWeatherProvider = WeatherProvider.DARK_SKY.name
            }
        }
    }
}
