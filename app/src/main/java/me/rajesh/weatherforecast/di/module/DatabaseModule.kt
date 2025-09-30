package me.rajesh.weatherforecast.di.module

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import me.rajesh.weatherforecast.data.db.AppDatabase
import me.rajesh.weatherforecast.data.db.dao.WeatherForecastDao
import me.rajesh.weatherforecast.data.db.service.DatabaseService
import me.rajesh.weatherforecast.data.db.service.DatabaseServiceImpl
import me.rajesh.weatherforecast.di.ApplicationContext
import javax.inject.Singleton


@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase  {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "weather_forecast_db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideWeatherForecastDao(appDatabase: AppDatabase): WeatherForecastDao {
        return appDatabase.weatherForecastDao()
    }

    @Singleton
    @Provides
    fun provideDatabaseService(appDatabase: AppDatabase): DatabaseService {
        return DatabaseServiceImpl(appDatabase)
    }

}