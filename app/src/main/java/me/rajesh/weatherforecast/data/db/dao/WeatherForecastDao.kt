package me.rajesh.weatherforecast.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import me.rajesh.weatherforecast.data.db.entity.WeatherForecastEntry

@Dao
interface WeatherForecastDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(weatherForecastEntry: WeatherForecastEntry)

    @Query("SELECT * FROM weather_forecast")
    fun getWeatherForecast(): Flow<WeatherForecastEntry>

}