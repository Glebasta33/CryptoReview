package com.example.cryptoreview.data.services

import android.content.Context
import androidx.work.*
import com.example.cryptoreview.data.database.AppDatabase
import com.example.cryptoreview.data.database.CoinInfoDao
import com.example.cryptoreview.data.mappers.CoinMapper
import com.example.cryptoreview.data.network.ApiFactory
import com.example.cryptoreview.data.network.ApiService
import kotlinx.coroutines.delay

// система сама создаёт Worker. Если в него передать другие параметры, она этого сделать не сможет
class RefreshDataWorker(
    context: Context,
    workerParameters: WorkerParameters,
    private val apiService: ApiService,
    private val coinInfoDao: CoinInfoDao,
    private val mapper: CoinMapper
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        while (true) {
            try {
                val topCoins = apiService.getTopCoinsInfo(limit = 50)
                val fSyms = mapper.mapNamesListToString(topCoins)
                val jsonContainer = apiService.getFullPriceList(fromSymbols = fSyms)
                val dtoList = mapper.mapJsonContainerToList(jsonContainer)
                val dbModelList = dtoList.map {
                    mapper.mapDtoToDbModel(it)
                }
                coinInfoDao.insertPriceList(dbModelList)
            } catch (e: Exception) {
            }
            delay(10_000)
        }
    }

    companion object {
        const val NAME = "RefreshDataWorker"

        fun makeRequest(): OneTimeWorkRequest {
            return OneTimeWorkRequestBuilder<RefreshDataWorker>()
                .build()
        }
    }
}