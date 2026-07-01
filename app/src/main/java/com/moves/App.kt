package com.moves

import android.app.Application
import com.moves.domain.di.dataBaseModule
import com.moves.domain.di.networkModule
import com.moves.domain.di.repositoriesModule
import com.moves.domain.di.viewModelsModule
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