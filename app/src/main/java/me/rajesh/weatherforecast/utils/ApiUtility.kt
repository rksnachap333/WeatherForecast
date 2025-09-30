package me.rajesh.weatherforecast.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import me.rajesh.weatherforecast.data.model.WeatherItem
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale
import kotlin.collections.filter

fun getThreeDayForecast(forecastList: List<WeatherItem>): List<WeatherItem> {

    val calendar = Calendar.getInstance()
    val today = getFormattedDate(calendar)

    calendar.add(Calendar.DAY_OF_YEAR, 1)
    val tomorrow = getFormattedDate(calendar)

    calendar.add(Calendar.DAY_OF_YEAR, 1)
    val dayAfterTomorrow = getFormattedDate(calendar)

//    val filteredForecasts = forecastList.filter { forecast ->
//        val forecastDate = forecast.dt_txt.split(" ")[0]
//        forecastDate == today || forecastDate == tomorrow || forecastDate == dayAfterTomorrow
//    }

    val threeDayData = listOf(
        forecastList.firstOrNull { it.dt_txt.split(" ")[0] == today },
        forecastList.firstOrNull { it.dt_txt.split(" ")[0] == tomorrow },
        forecastList.firstOrNull { it.dt_txt.split(" ")[0] == dayAfterTomorrow }
    ).filterNotNull()

    return threeDayData
}

fun getFormattedDate(calendar: Calendar): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return sdf.format(calendar.time)
}

fun String.firstCharToUpperCase(): String {
    return if (this.isNotEmpty()) {
        this.replaceFirstChar { it.uppercaseChar() }
    } else {
        this
    }
}

fun getDayOfWeekFromDate(dateString: String): String {

    return try {

        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val date = formatter.parse(dateString) ?: return ""
        val calendar = Calendar.getInstance()
        calendar.time = date

        val dayNum = calendar.get(Calendar.DAY_OF_WEEK)
        calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault()) ?: ""
    } catch (e: Exception) {
        Log.e("DateDebug", "Error: ${e.message}")
        ""
    }
}


fun isNetworkAvailable(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork
    val capabilities = connectivityManager.getNetworkCapabilities(network)
    return capabilities != null && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
}

