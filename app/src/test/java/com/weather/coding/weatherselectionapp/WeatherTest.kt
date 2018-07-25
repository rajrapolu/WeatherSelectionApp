package com.weather.coding.weatherselectionapp

import org.junit.After
import org.junit.Before
import java.io.File

abstract class WeatherTest {

    @Before
    abstract fun initSetUp()

    @After
    abstract fun tearDown()

    /**
     * Helps in reading json file, and converting the json file to string
     * @param pathToResource path of the json file
     */
    protected fun getJson(pathToResource: String): String {
        val uri = this.javaClass.classLoader.getResource(pathToResource)
        val openWeatherFile = File(uri.path)
        return openWeatherFile.readBytes().toString()
    }
}