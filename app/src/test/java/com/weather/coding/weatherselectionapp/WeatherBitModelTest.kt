package com.weather.coding.weatherselectionapp

import com.weather.coding.weatherselectionapp.dataclasses.WeatherBitDTO
import com.weather.coding.weatherselectionapp.weatherproviderfactory.WeatherBitProvider
import org.junit.Assert.assertEquals
import org.junit.Test

class WeatherBitModelTest : WeatherTest() {
    lateinit var mWeatherBit: WeatherBitProvider

    override fun initSetUp() {
        mWeatherBit = WeatherBitProvider()
    }

    override fun tearDown() {
    }

    @Test
    fun testWeatherBit() {
        val weatherBitDTO: WeatherBitDTO? =
                mWeatherBit.getWeatherServiceEndPoint("boston").execute().body()

        assertEquals("Boston", weatherBitDTO?.data?.get(0)?.cityName)
    }
}