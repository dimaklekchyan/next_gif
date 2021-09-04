package com.klekchyan.nextgif

import android.app.Application
import android.content.Context
import com.klekchyan.nextgif.dagger.AppComponent
import com.klekchyan.nextgif.dagger.DaggerAppComponent
import timber.log.Timber

class MainApp: Application() {

    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.create()
        Timber.plant(Timber.DebugTree())
    }

}

val Context.appComponent: AppComponent
    get() = when(this){
        is MainApp -> appComponent
        else -> applicationContext.appComponent
    }