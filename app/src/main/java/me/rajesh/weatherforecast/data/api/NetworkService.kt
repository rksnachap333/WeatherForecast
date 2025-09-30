package me.rajesh.weatherforecast.data.api

import me.rajesh.weatherforecast.data.model.WeatherForecastResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {

    @GET("forecast")
    suspend fun getWeatherReport(
        @Query(value = "q") city: String,
        @Query("apiKey") apiKey: String,
        @Query("units") units: String
    ): WeatherForecastResponse


}