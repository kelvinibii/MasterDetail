package com.example.kelvindu.masterdetail

import android.app.Application
import com.example.kelvindu.masterdetail.inject.component.ApplicationComponent
import com.example.kelvindu.masterdetail.inject.component.DaggerApplicationComponent
import com.example.kelvindu.masterdetail.inject.module.ApplicationModule
import timber.log.Timber

/**
 * Created by KelvinDu on 10/17/2017.
 */
class App: Application() {
    lateinit var component: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        instance = this
        setupInjector()

        if (BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun setupInjector(){
        component = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this)).build()
        component.inject(this)
    }

    companion object {
        lateinit var instance : App private set
    }
}