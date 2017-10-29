package com.example.kelvindu.masterdetail.inject.component

import com.example.kelvindu.masterdetail.App
import com.example.kelvindu.masterdetail.inject.module.ApplicationModule
import dagger.Component

/**
 * Created by KelvinDu on 10/17/2017.
 */
@Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationComponent {
    fun inject(app: App)
}