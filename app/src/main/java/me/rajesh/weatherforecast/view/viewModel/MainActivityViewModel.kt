package me.rajesh.weatherforecast.view.viewModel

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import me.rajesh.weatherforecast.data.model.WeatherItem
import me.rajesh.weatherforecast.data.repository.local.WeatherForecastLocalRepo
import me.rajesh.weatherforecast.data.repository.remote.WeatherReportRepository
import me.rajesh.weatherforecast.utils.NetworkUtil
import me.rajesh.weatherforecast.utils.PreferenceManager
import me.rajesh.weatherforecast.utils.firstCharToUpperCase
import me.rajesh.weatherforecast.utils.getThreeDayForecast
import me.rajesh.weatherforecast.view.base.UiState
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(
    private val preferenceManager: PreferenceManager,
    private val networkUtil: NetworkUtil,
    private val weatherReportRepository: WeatherReportRepository,
    private val weatherForecastLocalRepo: WeatherForecastLocalRepo
) : ViewModel() {
    companion object {
        const val TAG = "MainActivityViewModel"
    }

    private val _uiState = MutableStateFlow<UiState<List<WeatherItem>>>(UiState.Loading)
    val uiSTate: StateFlow<UiState<List<WeatherItem>>> = _uiState

    private val _cityName = MutableStateFlow(preferenceManager.getLastCity() ?: "New Delhi")
    val cityName: StateFlow<String> = _cityName

    // Exposing an event that will trigger data fetching
    private val _fetchCityEvent =
        MutableSharedFlow<String>()
    val fetchCityEvent = _fetchCityEvent.asSharedFlow()


    init {
        viewModelScope.launch {
            fetchCityEvent.collect {
                fetchCityData(it)
            }
        }
        println("PreferenceManager : Last City === ${preferenceManager.getLastCity()}")
        fetchWeatherData()
    }

    private fun fetchWeatherData() {
        if (networkUtil.isNetworkAvailable()) {
            fetchWeatherReport(cityName.value)
        } else {
            fetchFromDB()
        }

    }

    fun fetchWeatherReport(city: String = "New Delhi", units: String = "metric") {

        println("Main Activity : Fetching data from server")

        viewModelScope.launch {
            _uiState.value = UiState.Loading
            delay(2000)
            weatherReportRepository.getWeatherReport(city, units)
                .catch {
                    println(TAG + " Exception: ${it.toString()}")
                    _uiState.value = UiState.Error(it.toString())
                }
                .collect {
                    try {
                        weatherForecastLocalRepo.insertWeatherForecast(it.toWeatherForecastEntry())
                    } catch (e: Exception) {
                        println(TAG + " Exception: ${e.toString()}")
                    }
                    preferenceManager.saveLastCity(it.city.name)
                    _cityName.value = it.city.name
                    _uiState.value = UiState.Success(getThreeDayForecast(it.list))
                }
        }
    }


    fun fetchFromDB() {
        println("Main Activity : Loading data from db")

        viewModelScope.launch {
            _uiState.value = UiState.Loading
            weatherForecastLocalRepo.getWeatherForecast()
                .catch {
                    println(TAG + " Exception: ${it.toString()}")
                    _uiState.value = UiState.Error(it.toString())
                }
                .collect {
                    if(it != null) {
                        it?.let{
                            _uiState.value =
                                UiState.Success(getThreeDayForecast(it.toWeatherForecastResponse().list))
                        }
                    } else{
                        _uiState.value = UiState.NoData
                    }

                }

        }
    }

    fun triggerFetchCityData(city: String) {
        viewModelScope.launch {
            _fetchCityEvent.emit(city)
        }
    }

    private fun fetchCityData(city: String) {
        _cityName.value = city.firstCharToUpperCase()
        fetchWeatherData()
    }

}


