package com.example.cryptoreview.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import com.example.cryptoreview.data.database.AppDatabase
import com.example.cryptoreview.data.database.CoinInfoDao
import com.example.cryptoreview.data.mappers.CoinMapper
import com.example.cryptoreview.data.network.ApiFactory
import com.example.cryptoreview.data.services.RefreshDataWorker
import com.example.cryptoreview.domain.CoinInfoEntity
import com.example.cryptoreview.domain.CoinRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    private val application: Application,
    private val coinInfoDao: CoinInfoDao,
    private val mapper: CoinMapper
): CoinRepository {

    //private val coinInfoDao = AppDatabase.getInstance(application).coinPriceInfoDao()
    private val apiService = ApiFactory.apiService
   // private val mapper = CoinMapper()

    override fun getCoinInfoList(): LiveData<List<CoinInfoEntity>> {
        return Transformations.map(coinInfoDao.getPriceList()) {
            it.map {
                mapper.mapDbModelToEntity(it)
            }
        }
    }

    override fun getCoinInfo(fromSymbol: String): LiveData<CoinInfoEntity> {
        return Transformations.map(coinInfoDao.getPriceInfoAboutCoin(fromSymbol)) {
            mapper.mapDbModelToEntity(it)
        }

    }

    override fun loadData() {
        val workManager = WorkManager.getInstance(application)
        workManager.enqueueUniqueWork(
            RefreshDataWorker.NAME,
            ExistingWorkPolicy.REPLACE,
            RefreshDataWorker.makeRequest() // 21:58
        )
    }
}