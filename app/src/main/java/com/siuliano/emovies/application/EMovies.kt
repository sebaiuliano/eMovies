package com.siuliano.emovies.application

import android.app.Application
import com.siuliano.emovies.BuildConfig
import com.siuliano.emovies.application.modules.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class EMovies : Application() {
    override fun onCreate() {
        super.onCreate()
        initiateKoin()
        initiateTimber()
    }

    private fun initiateKoin() {
        startKoin {
            androidContext(this@EMovies)
            modules(listOf(
                viewModelModule,
                networkModule,
                apiRepositoryModule,
                apiModule
            ))
        }
    }

    private fun initiateTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}