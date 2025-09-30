package me.rajesh.weatherforecast.utils

import android.content.Context
import android.net.ConnectivityManager
import me.rajesh.weatherforecast.di.ApplicationContext
import javax.inject.Inject

class NetworkUtil @Inject constructor(
    @ApplicationContext private val context: Context
) {

    fun isNetworkAvailable(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetworkInfo
        return activeNetwork?.isConnected == true
    }
}
