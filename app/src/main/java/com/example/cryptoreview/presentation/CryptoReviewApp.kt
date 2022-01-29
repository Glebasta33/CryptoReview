package com.example.cryptoreview.presentation

import android.app.Application
import androidx.work.Configuration
import com.example.cryptoreview.data.database.AppDatabase
import com.example.cryptoreview.data.mappers.CoinMapper
import com.example.cryptoreview.data.network.ApiFactory
import com.example.cryptoreview.data.services.RefreshDataWorkerFactory
import com.example.cryptoreview.di.DaggerApplicationComponent

class CryptoReviewApp : Application(), Configuration.Provider {
    val component by lazy {
        DaggerApplicationComponent.factory()
            .create(this)
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(RefreshDataWorkerFactory(
                ApiFactory.apiService,
                AppDatabase.getInstance(this).coinPriceInfoDao(),
                CoinMapper()
            )).build()
    }
}