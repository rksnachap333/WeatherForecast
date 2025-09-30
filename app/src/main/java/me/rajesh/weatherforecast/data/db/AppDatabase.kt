package me.rajesh.weatherforecast.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import me.rajesh.weatherforecast.data.db.dao.WeatherForecastDao
import me.rajesh.weatherforecast.data.db.entity.WeatherForecastEntry
import me.rajesh.weatherforecast.data.model.WeatherForecastResponse
import me.rajesh.weatherforecast.data.model.WeatherForecastResponseConverters


@Database(entities = [WeatherForecastEntry::class], version = 1, exportSchema = false)
@TypeConverters(WeatherForecastResponseConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun weatherForecastDao(): WeatherForecastDao
}
