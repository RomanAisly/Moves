package com.moves

import android.app.Application
import com.moves.core.di.dataBaseModule
import com.moves.core.di.networkModule
import com.moves.core.di.repositoriesModule
import com.moves.core.di.viewModelsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(networkModule, dataBaseModule, viewModelsModule, repositoriesModule)
        }
    }
}