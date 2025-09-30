package me.rajesh.weatherforecast.di.module

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import me.rajesh.weatherforecast.app.WeatherApplication
import me.rajesh.weatherforecast.data.api.NetworkService
import me.rajesh.weatherforecast.data.repository.local.WeatherForecastLocalRepo
import me.rajesh.weatherforecast.data.repository.remote.WeatherReportRepository
import me.rajesh.weatherforecast.di.ActivityContext
import me.rajesh.weatherforecast.di.ApplicationContext
import me.rajesh.weatherforecast.di.BaseUrl
import me.rajesh.weatherforecast.utils.AppConstant
import me.rajesh.weatherforecast.utils.NetworkUtil
import me.rajesh.weatherforecast.utils.PreferenceManager
import me.rajesh.weatherforecast.view.MainActivity
import me.rajesh.weatherforecast.view.base.ViewModelProviderFactory
import me.rajesh.weatherforecast.view.viewModel.MainActivityViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
class ApplicationModule(private val application: Application) {

    @ApplicationContext
    @Provides
    fun provideApplicationContext(application: Application): Context {
        return application.applicationContext
    }

    @BaseUrl
    @Provides
    fun provideBaseUrl(): String {
        return AppConstant.BASE_URL
    }

    @Singleton
    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Singleton
    @Provides
    fun provideNetworkService(
        @BaseUrl baseUrl: String,
        gsonConverterFactory: GsonConverterFactory
    ): NetworkService {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(gsonConverterFactory)
            .build()
            .create(NetworkService::class.java)

    }

    @Singleton
    @Provides
    fun provideWeatherReportRepository(
        networkService: NetworkService
    ): WeatherReportRepository {
        return WeatherReportRepository(networkService)
    }

    @Singleton
    @Provides
    fun provideNetworkUtil(@ApplicationContext context: Context): NetworkUtil {
        return NetworkUtil(context)
    }

    @Singleton
    @Provides
    fun providePreferenceManager(@ApplicationContext context: Context): PreferenceManager {
        return PreferenceManager(context)
    }


//    @Singleton
//    @Provides
//    fun provideMainActivityViewModel(
//        preferenceManager: PreferenceManager,
//        networkUtil: NetworkUtil,
//        weatherReportRepository: WeatherReportRepository,
//        weatherForecastLocalRepo: WeatherForecastLocalRepo
//    ): MainActivityViewModel {
//        return ViewModelProvider(
//            application.applicationContext as WeatherApplication,
//            ViewModelProviderFactory(MainActivityViewModel::class) {
//                MainActivityViewModel(
//                    preferenceManager,
//                    networkUtil,
//                    weatherReportRepository,
//                    weatherForecastLocalRepo
//                )
//            }
//        )[MainActivityViewModel::class.java]
//    }
}