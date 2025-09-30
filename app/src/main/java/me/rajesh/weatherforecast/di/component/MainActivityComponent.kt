package me.rajesh.weatherforecast.di.component

import android.content.Context
import dagger.Component
import me.rajesh.weatherforecast.view.MainActivity
import me.rajesh.weatherforecast.di.ActivityContext
import me.rajesh.weatherforecast.di.ActivityScope
import me.rajesh.weatherforecast.di.module.MainActivityModule


@ActivityScope
@Component(dependencies = [ApplicationComponent::class], modules = [MainActivityModule::class])
interface MainActivityComponent {

    fun inject(activity: MainActivity)


}