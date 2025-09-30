package me.rajesh.weatherforecast.app

import android.app.Application
import me.rajesh.weatherforecast.data.api.NetworkService
import me.rajesh.weatherforecast.data.repository.remote.WeatherReportRepository
import me.rajesh.weatherforecast.di.component.ApplicationComponent
import me.rajesh.weatherforecast.di.component.DaggerApplicationComponent
import me.rajesh.weatherforecast.di.module.ApplicationModule
import javax.inject.Inject

class WeatherApplication : Application() {

    lateinit var applicationComponent: ApplicationComponent

    @Inject
    lateinit var weatherReportRepository: WeatherReportRepository

    override fun onCreate() {

        injectDependencies()
        super.onCreate()

        println("WeatherApplication Inside onCreate == ${weatherReportRepository.hashCode()}")
    }

    private fun injectDependencies() {
        applicationComponent = DaggerApplicationComponent
            .builder()
            .application(this)
            .applicationModule(ApplicationModule(this))
            .build()

        applicationComponent.inject(this)
    }
}