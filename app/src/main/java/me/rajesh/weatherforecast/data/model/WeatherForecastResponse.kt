package me.rajesh.weatherforecast.data.model

import me.rajesh.weatherforecast.data.db.entity.WeatherForecastEntry

data class WeatherForecastResponse(
    val cod: String,
    val message: Int,
    val cnt: Int,
    val list: List<WeatherItem>,
    val city: City
){
    fun toWeatherForecastEntry(): WeatherForecastEntry {
        return WeatherForecastEntry(weatherForecastResponse = this)
    }
}

data class City(
    val name: String,
    val country: String
)
