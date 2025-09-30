package me.rajesh.weatherforecast.data.repository.remote

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import me.rajesh.weatherforecast.data.api.NetworkService
import me.rajesh.weatherforecast.data.model.WeatherForecastResponse
import me.rajesh.weatherforecast.data.model.WeatherItem
import me.rajesh.weatherforecast.utils.AppConstant
import me.rajesh.weatherforecast.utils.getThreeDayForecast
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherReportRepository @Inject constructor(
    private val networkService: NetworkService
) {

    fun getWeatherReport(city: String, units: String): Flow<WeatherForecastResponse> {

        return flow {
            emit(networkService.getWeatherReport(city, AppConstant.WEATHER_API_KEY, units))
        }

    }
}