package com.weather.coding.weatherselectionapp

import com.weather.coding.weatherselectionapp.dataclasses.LocationWeatherDTO
import com.weather.coding.weatherselectionapp.weatherproviderfactory.OpenWeatherProvider
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class OpenWeatherModelTest : WeatherTest() {
    lateinit var mOpenWeather: OpenWeatherProvider

    override fun initSetUp() {
        mOpenWeather = OpenWeatherProvider()
    }

    override fun tearDown() {
    }

    @Test
    fun testOpenWeatherModel() {
        val locationWeatherDTO: LocationWeatherDTO? =
                mOpenWeather.getWeatherServiceEndPoint("boston", "us").execute().body()
        assertEquals("Boston", locationWeatherDTO?.city?.name)
        // To fail the test case
//        assertEquals("Lenexa", locationWeatherDTO?.city?.name)
    }

    @Test
    fun testFailureOpenWeatherModel() {
        val locationWeatherDTO: LocationWeatherDTO? =
                mOpenWeather.getWeatherServiceEndPoint("boston", "ma").execute().body()

        assertEquals(null, locationWeatherDTO)
    }
}
