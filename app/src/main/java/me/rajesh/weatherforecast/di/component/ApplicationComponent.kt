package me.rajesh.weatherforecast.di.component

import android.app.Application
import android.content.Context
import dagger.BindsInstance
import dagger.Component
import me.rajesh.weatherforecast.app.WeatherApplication
import me.rajesh.weatherforecast.data.api.NetworkService
import me.rajesh.weatherforecast.data.db.service.DatabaseService
import me.rajesh.weatherforecast.data.repository.local.WeatherForecastLocalRepo
import me.rajesh.weatherforecast.data.repository.remote.WeatherReportRepository
import me.rajesh.weatherforecast.di.ApplicationContext
import me.rajesh.weatherforecast.di.module.ApplicationModule
import me.rajesh.weatherforecast.di.module.DatabaseModule
import me.rajesh.weatherforecast.utils.NetworkUtil
import me.rajesh.weatherforecast.utils.PreferenceManager
import me.rajesh.weatherforecast.view.viewModel.MainActivityViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, DatabaseModule::class])
interface ApplicationComponent {

    fun inject(application: WeatherApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun applicationModule(applicationModule: ApplicationModule): Builder
        fun build(): ApplicationComponent
    }

    @ApplicationContext
    fun getContext(): Context

    fun getNetworkService(): NetworkService

    fun getWeatherReportRepository(): WeatherReportRepository

    fun getDatabaseService(): DatabaseService

    fun getWeatherForecastLocalRepo(): WeatherForecastLocalRepo

    fun getNetworkUtil(): NetworkUtil

    fun getPreferenceManager(): PreferenceManager

}