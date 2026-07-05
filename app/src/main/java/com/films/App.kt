package com.films

import android.app.Application
import com.films.core.di.dataBaseModule
import com.films.core.di.networkModule
import com.films.core.di.repositoriesModule
import com.films.core.di.settingsModule
import com.films.core.di.viewModelsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                networkModule,
                dataBaseModule,
                viewModelsModule,
                repositoriesModule,
                settingsModule
            )
        }
    }
}