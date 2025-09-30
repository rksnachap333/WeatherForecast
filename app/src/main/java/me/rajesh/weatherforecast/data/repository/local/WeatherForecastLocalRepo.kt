package me.rajesh.weatherforecast.data.repository.local

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import me.rajesh.weatherforecast.data.db.dao.WeatherForecastDao
import me.rajesh.weatherforecast.data.db.entity.WeatherForecastEntry
import me.rajesh.weatherforecast.data.db.service.DatabaseServiceImpl
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class WeatherForecastLocalRepo @Inject constructor(
    private val appDatabaseServiceImpl: DatabaseServiceImpl
) {

    suspend fun insertWeatherForecast(weatherForecastEntry: WeatherForecastEntry) {
        withContext(Dispatchers.IO) {
            appDatabaseServiceImpl.insertWeatherForecast(weatherForecastEntry)
        }

    }

    fun getWeatherForecast(): Flow<WeatherForecastEntry> {
        return appDatabaseServiceImpl.getWeatherForecast()
    }

}