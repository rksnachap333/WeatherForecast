package me.rajesh.weatherforecast.data.model

data class WeatherItem(
    val dt_txt: String,
    val main: Main,
    val weather: List<Weather>,

) {
    data class Main(
        val temp: Double,
        val temp_max: Double,
        val temp_min: Double
    )

    data class Weather(
        val description: String,
        val icon: String
    )
}
