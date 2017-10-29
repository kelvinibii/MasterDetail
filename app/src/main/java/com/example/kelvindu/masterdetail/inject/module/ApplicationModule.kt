package com.example.kelvindu.masterdetail.inject.module

import android.app.Application
import com.example.kelvindu.masterdetail.App
import com.example.kelvindu.masterdetail.inject.scope.PerApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by KelvinDu on 10/17/2017.
 */
@Module
class ApplicationModule(private val app: App) {
    @Provides
    @Singleton
    @PerApplication
    fun provideApplicationContext(): Application = app
}