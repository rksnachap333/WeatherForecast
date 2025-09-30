package me.rajesh.weatherforecast.di.module

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import me.rajesh.weatherforecast.data.repository.local.WeatherForecastLocalRepo
import me.rajesh.weatherforecast.data.repository.remote.WeatherReportRepository
import me.rajesh.weatherforecast.di.ActivityContext
import me.rajesh.weatherforecast.utils.NetworkUtil
import me.rajesh.weatherforecast.utils.PreferenceManager
import me.rajesh.weatherforecast.view.base.ViewModelProviderFactory
import me.rajesh.weatherforecast.view.viewModel.MainActivityViewModel


@Module
class MainActivityModule(private val activity: ComponentActivity) {

    @ActivityContext
    @Provides
    fun provideContext(): Context {
        return activity
    }


    @Provides
    fun provideMainActivityViewModel(
        preferenceManager: PreferenceManager,
        networkUtil: NetworkUtil,
        weatherReportRepository: WeatherReportRepository,
        weatherForecastLocalRepo: WeatherForecastLocalRepo
    ): MainActivityViewModel {
        return ViewModelProvider(
            activity,
            ViewModelProviderFactory(MainActivityViewModel::class) {
                MainActivityViewModel(
                    preferenceManager,
                    networkUtil,
                    weatherReportRepository,
                    weatherForecastLocalRepo
                )
            }
        )[MainActivityViewModel::class.java]
    }
}