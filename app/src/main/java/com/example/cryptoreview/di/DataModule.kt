package com.example.cryptoreview.di

import android.app.Application
import com.example.cryptoreview.data.database.AppDatabase
import com.example.cryptoreview.data.database.CoinInfoDao
import com.example.cryptoreview.data.network.ApiFactory
import com.example.cryptoreview.data.network.ApiService
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
class DataModule {
    @Provides
    fun provideDao(application: Application): CoinInfoDao {
        return AppDatabase.getInstance(application).coinPriceInfoDao()
    }

    companion object {
        @Provides
        fun provideApiService(): ApiService {
            return ApiFactory.apiService
        }
    }
}