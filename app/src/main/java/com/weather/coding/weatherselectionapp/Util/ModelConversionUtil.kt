package com.weather.coding.weatherselectionapp.Util

import com.weather.coding.weatherselectionapp.CurrentWeatherDTO
import com.weather.coding.weatherselectionapp.dataobjects.DarkSkyModel
import com.weather.coding.weatherselectionapp.dataobjects.FiveDayWeatherModel
import com.weather.coding.weatherselectionapp.dataobjects.OpenWeatherModel

class ModelConversionUtil {

    companion object {

        fun convertOpenWeatherResponse(locationWeatherDTO: OpenWeatherModel.LocationWeatherDTO): CurrentWeatherDTO {
            return CurrentWeatherDTO(locationWeatherDTO.weatherListDTO[0].mainWeather.temp.toString(), locationWeatherDTO.city.name, locationWeatherDTO.weatherListDTO[0].wind.speed.toString(), locationWeatherDTO.weatherListDTO[0].wind.degree.toString(), locationWeatherDTO.weatherListDTO[0].mainWeather.tempMin.toString(), locationWeatherDTO.weatherListDTO[0].mainWeather.tempMax.toString())
        }

        fun convertDarkSkyDTO(darkSkyDTO: DarkSkyModel.DarkSkyDTO): CurrentWeatherDTO? {
            return CurrentWeatherDTO(darkSkyDTO.darkSkyCurrentWeatherDTO.temperature.toString(), darkSkyDTO.latitude.toString(), darkSkyDTO.darkSkyCurrentWeatherDTO.windSpeed.toString(), null, null, null)
        }

        fun convertFiveDayWeatherDTO(fiveDayWeatherDTO: FiveDayWeatherModel.FiveDayWeatherDataDTO): CurrentWeatherDTO? {
            return CurrentWeatherDTO(fiveDayWeatherDTO.data.temperature, fiveDayWeatherDTO.data.location, fiveDayWeatherDTO.data.wind, null, null, null)
        }
    }
}