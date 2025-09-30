package me.rajesh.weatherforecast.utils

import android.content.Context
import android.content.SharedPreferences
import me.rajesh.weatherforecast.di.ApplicationContext
import javax.inject.Inject
import androidx.core.content.edit

class PreferenceManager @Inject constructor(
    @ApplicationContext private val context: Context
)
{
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("WeatherForecastSharedPref", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_LAST_CITY = "last_city"
    }

    fun saveLastCity(city: String) {
        sharedPreferences.edit {
            putString(KEY_LAST_CITY, city)
        }
    }

    fun getLastCity(): String? {
        return sharedPreferences.getString(KEY_LAST_CITY, "New Delhi")
    }
}