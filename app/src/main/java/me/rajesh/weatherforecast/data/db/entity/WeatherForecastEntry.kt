package me.rajesh.weatherforecast.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import me.rajesh.weatherforecast.data.model.WeatherForecastResponse
import me.rajesh.weatherforecast.data.model.WeatherForecastResponseConverters

@Entity(tableName = "weather_forecast")
data class WeatherForecastEntry(
    @PrimaryKey val id: Int = 1,

    @TypeConverters(WeatherForecastResponseConverters::class)
    val weatherForecastResponse: WeatherForecastResponse
) {
    fun toWeatherForecastResponse(): WeatherForecastResponse {
        return weatherForecastResponse
    }
}



