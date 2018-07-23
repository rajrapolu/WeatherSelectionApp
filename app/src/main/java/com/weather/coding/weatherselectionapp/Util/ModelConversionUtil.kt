package com.weather.coding.weatherselectionapp.Util

import com.weather.coding.weatherselectionapp.CurrentWeatherDTO
import com.weather.coding.weatherselectionapp.DarkSkyModel
import com.weather.coding.weatherselectionapp.OpenWeatherModel

class ModelConversionUtil {

    companion object {

        fun convertOpenWeatherResponse(locationWeatherDTO: OpenWeatherModel.LocationWeatherDTO): CurrentWeatherDTO {
            return CurrentWeatherDTO(locationWeatherDTO.weatherListDTO[0].mainWeather.temp, locationWeatherDTO.city.name, locationWeatherDTO.weatherListDTO[0].wind.speed)
        }

        fun convertDarkSkyDTO(darkSkyDTO: DarkSkyModel.DarkSkyDTO): CurrentWeatherDTO? {
            return CurrentWeatherDTO(darkSkyDTO.darkSkyCurrentWeatherDTO.temperature, darkSkyDTO.latitude.toString(), darkSkyDTO.darkSkyCurrentWeatherDTO.windSpeed)
        }
    }
}