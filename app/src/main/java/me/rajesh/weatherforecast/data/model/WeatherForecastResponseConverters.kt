package me.rajesh.weatherforecast.data.model

import androidx.room.TypeConverter
import com.google.gson.Gson

class WeatherForecastResponseConverters {

    @TypeConverter
    fun fromWeatherForecastResponse(value: WeatherForecastResponse): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toWeatherForecastResponse(value: String): WeatherForecastResponse {
        return Gson().fromJson(value, WeatherForecastResponse::class.java)
    }

}