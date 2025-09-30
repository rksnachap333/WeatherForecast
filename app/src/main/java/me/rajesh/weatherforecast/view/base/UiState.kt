package me.rajesh.weatherforecast.view.base

sealed interface UiState<out T> {

    data class Success<T>(val data : T) : UiState<T>

    data class Error(val message : String) : UiState<Nothing>

    object Loading : UiState<Nothing>

    object NoData : UiState<Nothing>

}