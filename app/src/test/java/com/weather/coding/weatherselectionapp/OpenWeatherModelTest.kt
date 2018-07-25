package com.weather.coding.weatherselectionapp

import com.weather.coding.weatherselectionapp.dataobjects.OpenWeatherModel
import com.weather.coding.weatherselectionapp.networkcalls.RetrofitService
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.File

class OpenWeatherModelTest {
    lateinit var mMockServer: MockWebServer

    @Before
    fun initSetUp() {
        mMockServer = MockWebServer()
        mMockServer.start()
    }

    @Test
    fun testOpenWeatherModel() {
        val mockResponse = MockResponse()
                .setResponseCode(200)
                .setBody(getJson("open_weather_success.json"))

        mMockServer.enqueue(mockResponse)

        val locationWeatherDTO: OpenWeatherModel.LocationWeatherDTO? = RetrofitService()
                .getWeatherService("http://api.openweathermap.org/")
                ?.getOpenWeatherData("boston", "imperial", "f12e77308bfba46a4939b7de916db179")
                ?.execute()
                ?.body()

        assertEquals("Boston", locationWeatherDTO?.city?.name)
        //to check failure of the test
//        assertEquals("Lenexa", locationWeatherDTO?.city?.name)
    }

    @Test
    fun testFailureOpenWeatherModel() {
        val mockResponse = MockResponse().setResponseCode(404).setBody(getJson("open_weather_failure.json"))

        mMockServer.enqueue(mockResponse)

        val locationWeatherDTO: OpenWeatherModel.LocationWeatherDTO? = RetrofitService()
                .getWeatherService("http://api.openweathermap.org/")
                ?.getOpenWeatherData("boston,ma", "imperial", "f12e77308bfba46a4939b7de916db179")
                ?.execute()
                ?.body()

        assertEquals(null, locationWeatherDTO)
    }

    /**
     * Helps in reading json file, and converting the json file to string
     * @param pathToResource path of the json file
     */
    private fun getJson(pathToResource: String): String {
        val uri = this.javaClass.classLoader.getResource(pathToResource)
        val openWeatherFile = File(uri.path)
        return openWeatherFile.readBytes().toString()
    }

    @After
    fun shutDownTest() {
        mMockServer.shutdown()
    }
}
