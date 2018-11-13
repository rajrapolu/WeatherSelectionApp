package com.weather.coding.weatherselectionapp.Util

import com.weather.coding.weatherselectionapp.CurrentWeatherDTO
import com.weather.coding.weatherselectionapp.WeatherForecastModel
import com.weather.coding.weatherselectionapp.dataclasses.*

class ModelConversionUtil {

    companion object {

        fun convertOpenWeatherResponse(locationWeatherDTO: LocationWeatherDTO): CurrentWeatherDTO {
            return CurrentWeatherDTO(locationWeatherDTO.weatherListDTO[0].mainWeather.temp.toString(), locationWeatherDTO.city.name, locationWeatherDTO.weatherListDTO[0].wind.speed.toString(), locationWeatherDTO.weatherListDTO[0].wind.degree.toString(), locationWeatherDTO.weatherListDTO[0].mainWeather.tempMin.toString(), locationWeatherDTO.weatherListDTO[0].mainWeather.tempMax.toString())
        }

        fun convertDarkSkyDTO(darkSkyDTO: DarkSkyDTO): CurrentWeatherDTO {
            return CurrentWeatherDTO(darkSkyDTO.darkSkyCurrentWeatherDTO.temperature.toString(), "${darkSkyDTO.latitude}, ${darkSkyDTO.longitude}", darkSkyDTO.darkSkyCurrentWeatherDTO.windSpeed.toString(), null, null, null)
        }

        fun convertFiveDayWeatherDTO(fiveDayWeatherDTO: FiveDayWeatherDataDTO): CurrentWeatherDTO {
            return CurrentWeatherDTO(fiveDayWeatherDTO.data.temperature, fiveDayWeatherDTO.data.location, fiveDayWeatherDTO.data.wind, null, null, null)
        }

        fun convertWeatherBitResponse(weatherBitDTO: WeatherBitDTO): CurrentWeatherDTO {
            return CurrentWeatherDTO(weatherBitDTO.data[0].temp.toString(), weatherBitDTO.data[0].cityName, weatherBitDTO.data[0].windSpeed.toString(), null, null, null)
        }

        /**
         * converts weather forecast from weatherbit provider to weather forecast
         */
        fun convertWeatherBitToForecast(weatherBitForecastDTO: WeatherBitForecastDTO): WeatherForecastModel.WeatherForecastDTO {
            val dayForecastList: MutableList<WeatherForecastModel.DayForecast> = ArrayList()
            val weatherForecastData = weatherBitForecastDTO.weatherForecastData
            if (weatherForecastData.isNotEmpty()) {
                for (i in weatherBitForecastDTO.weatherForecastData.indices) {
                    val weatherForecast = weatherForecastData[i]
                    dayForecastList.add(WeatherForecastModel.DayForecast(weatherForecast.temp.toString(),
                            weatherForecast.minTemp.toString(), weatherForecast.maxTemp.toString(), weatherForecast.date))
                }
            }
            return WeatherForecastModel.WeatherForecastDTO(weatherBitForecastDTO.cityName, dayForecastList)
        }
    }
}