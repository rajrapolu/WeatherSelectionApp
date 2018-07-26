package com.weather.coding.weatherselectionapp.weatherforecast

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.weather.coding.weatherselectionapp.R
import com.weather.coding.weatherselectionapp.Util.UnitsUtil
import com.weather.coding.weatherselectionapp.WeatherForecastModel
import kotlinx.android.synthetic.main.weather_forecast_item_view.view.*

class WeatherForecastAdapter(var mDayForecastItems: MutableList<WeatherForecastModel.DayForecast>?): RecyclerView.Adapter<WeatherForecastAdapter.WeatherForecastViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherForecastViewHolder {
        return WeatherForecastViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.weather_forecast_item_view, parent, false))
    }

    override fun getItemCount(): Int {
        return mDayForecastItems?.size ?: 0
    }

    override fun onBindViewHolder(holder: WeatherForecastViewHolder, position: Int) {
        holder.bind(mDayForecastItems?.get(position))
    }

    fun updateRecords(forecastItems: MutableList<WeatherForecastModel.DayForecast>?) {
        if (forecastItems != null && forecastItems.isNotEmpty()) {
            mDayForecastItems = forecastItems
            notifyDataSetChanged()
        }
    }

    class WeatherForecastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(dayForecast: WeatherForecastModel.DayForecast?) {
            if (dayForecast != null) {
                val context = itemView.context
                if (context != null) {
                    val tempUnits = UnitsUtil.getFahrenheitUnit()
                    itemView.weather_item_date.text = context.getString(R.string.date_label, dayForecast.date)
                    itemView.weather_item_temp.text = context.getString(R.string.current_temp_label, dayForecast.temp, tempUnits)
                    itemView.weather_min_temp.text = context.getString(R.string.current_weather_min_label, dayForecast.minTemp, tempUnits)
                    itemView.weather_max_temp.text = context.getString(R.string.current_weather_max_label, dayForecast.maxTemp, tempUnits)
                }
            }
        }
    }
}