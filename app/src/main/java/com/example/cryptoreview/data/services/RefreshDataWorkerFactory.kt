package com.example.cryptoreview.data.services

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.example.cryptoreview.data.database.CoinInfoDao
import com.example.cryptoreview.data.mappers.CoinMapper
import com.example.cryptoreview.data.network.ApiService

class RefreshDataWorkerFactory(
    private val apiService: ApiService,
    private val coinInfoDao: CoinInfoDao,
    private val mapper: CoinMapper
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return RefreshDataWorker(
            appContext,
            workerParameters,
            apiService,
            coinInfoDao,
            mapper
        )
    }

}