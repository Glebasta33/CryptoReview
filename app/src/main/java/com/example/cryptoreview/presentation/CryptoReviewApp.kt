package com.example.cryptoreview.presentation

import android.app.Application
import androidx.work.Configuration
import com.example.cryptoreview.data.workers.CryptoReviewWorkerFactory
import com.example.cryptoreview.di.DaggerApplicationComponent
import javax.inject.Inject

class CryptoReviewApp : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: CryptoReviewWorkerFactory

    val component by lazy {
        DaggerApplicationComponent.factory()
            .create(this)
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(workerFactory).build()
    }

    override fun onCreate() {
        component.inject(this)
        super.onCreate()
    }
}