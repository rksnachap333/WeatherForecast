package me.rajesh.weatherforecast.data.db.service

import kotlinx.coroutines.flow.Flow
import me.rajesh.weatherforecast.data.db.entity.WeatherForecastEntry

interface DatabaseService {

    fun insertWeatherForecast(weatherForecastEntry: WeatherForecastEntry)

    fun getWeatherForecast(): Flow<WeatherForecastEntry>

}