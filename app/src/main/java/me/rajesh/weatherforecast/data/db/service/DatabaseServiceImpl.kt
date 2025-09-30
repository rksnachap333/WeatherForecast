package me.rajesh.weatherforecast.data.db.service

import kotlinx.coroutines.flow.Flow
import me.rajesh.weatherforecast.data.db.AppDatabase
import me.rajesh.weatherforecast.data.db.entity.WeatherForecastEntry
import javax.inject.Inject

class DatabaseServiceImpl @Inject constructor(
    private val appDatabase: AppDatabase
) : DatabaseService {

    override fun insertWeatherForecast(weatherForecastEntry: WeatherForecastEntry) {
        return appDatabase.weatherForecastDao().insert(weatherForecastEntry)
    }

    override fun getWeatherForecast(): Flow<WeatherForecastEntry> {
        return appDatabase.weatherForecastDao().getWeatherForecast()
    }


}