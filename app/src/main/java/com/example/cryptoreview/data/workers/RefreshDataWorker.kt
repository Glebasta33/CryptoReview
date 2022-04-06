package com.example.cryptoreview.data.workers

import android.content.Context
import androidx.work.*
import com.example.cryptoreview.data.database.CoinInfoDao
import com.example.cryptoreview.data.mappers.CoinMapper
import com.example.cryptoreview.data.network.ApiService
import kotlinx.coroutines.delay
import javax.inject.Inject

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
            delay(2_000)
        }
    }

    companion object {
        const val NAME = "RefreshDataWorker"

        fun makeRequest(): OneTimeWorkRequest {
            return OneTimeWorkRequestBuilder<RefreshDataWorker>()
                .build()
        }
    }

    class Factory @Inject constructor(
        private val apiService: ApiService,
        private val coinInfoDao: CoinInfoDao,
        private val mapper: CoinMapper
    ) : ChildWorkerFactory {
        override fun create(
            context: Context,
            workerParameters: WorkerParameters
        ): ListenableWorker {
            return RefreshDataWorker(
                context,
                workerParameters,
                apiService,
                coinInfoDao,
                mapper
            )
        }
    }
}