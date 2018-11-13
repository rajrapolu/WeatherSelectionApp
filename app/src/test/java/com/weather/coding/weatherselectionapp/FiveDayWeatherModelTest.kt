package com.weather.coding.weatherselectionapp

import com.weather.coding.weatherselectionapp.dataclasses.FiveDayWeatherDataDTO
import com.weather.coding.weatherselectionapp.weatherproviderfactory.FiveDayWeatherProvider
import org.junit.Assert.assertEquals
import org.junit.Test

class FiveDayWeatherModelTest : WeatherTest() {
    lateinit var mFiveDayWeather: FiveDayWeatherProvider

    override fun initSetUp() {
        mFiveDayWeather = FiveDayWeatherProvider()
    }

    override fun tearDown() {
    }

    @Test
    fun testFiveDayWeather() {

        val fiveDayWeatherDTO: FiveDayWeatherDataDTO? =
                mFiveDayWeather.getWeatherServiceEndPoint("lenexa").execute().body()

        assertEquals("Clear", fiveDayWeatherDTO?.data?.skytext)
        assertEquals("Lenexa, KS", fiveDayWeatherDTO?.data?.location)
    }
}