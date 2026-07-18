package com.films

import android.app.Application
import com.films.components.viewModelsModule
import com.films.local.dataBaseModule
import com.films.local.networkModule
import com.films.local.repositoriesModule
import com.films.local.settingsModule
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