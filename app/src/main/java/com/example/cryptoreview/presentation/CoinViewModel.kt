package com.example.cryptoreview.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.cryptoreview.data.network.ApiFactory
import com.example.cryptoreview.data.database.AppDatabase
import com.example.cryptoreview.data.network.model.CoinInfoDto
import com.example.cryptoreview.data.network.model.CoinInfoJsonContainerDto
import com.google.gson.Gson
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class CoinViewModel() : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private lateinit var db: AppDatabase
    lateinit var liveDataPriceList: LiveData<List<CoinInfoDto>>

    fun getDetailInfo(fSym: String): LiveData<CoinInfoDto> {
        return db.coinPriceInfoDao().getPriceInfoAboutCoin(fSym)
    }

    fun createDatabase(application: Application) {
        db = AppDatabase.getInstance(application)
        liveDataPriceList = db.coinPriceInfoDao().getPriceList()
    }

    init {
        loadData()
    }

    private fun loadData() {
        val disposable = ApiFactory.apiService.getTopCoinsInfo()
                .map { it.names?.map { it.coinName?.name }?.joinToString(",") }
                .flatMap { ApiFactory.apiService.getFullPriceList(fromSymbols = it!!) }
                .map { getPriceListFromRawData(it) }
                .delaySubscription(10, TimeUnit.SECONDS)
                .repeat() // повторяет код выше
                .retry() // возобнавляет после ошибки
                .subscribeOn(Schedulers.io())
                .subscribe({
                    db.coinPriceInfoDao().insertPriceList(it)
                    Log.v("TEST_OF_LOADING_DATA", it.toString())
                }, {
                    Log.v("TEST_OF_LOADING_DATA", it.message.toString())
                })
        compositeDisposable.addAll(disposable)
    }

    private fun getPriceListFromRawData(coinInfoJsonContainer: CoinInfoJsonContainerDto): List<CoinInfoDto> {
        val result = ArrayList<CoinInfoDto>();
        val jsonObject = coinInfoJsonContainer.json ?: return result
        val coinKeySet = jsonObject.keySet()
        for (coinKey in coinKeySet) {
            val currencyJson = jsonObject.getAsJsonObject(coinKey)
            val currencyKeySet = currencyJson.keySet()
            for (currencyKey in currencyKeySet) {
                val priceInfo = Gson().fromJson(
                        currencyJson.getAsJsonObject(currencyKey),
                        CoinInfoDto::class.java)
                result.add(priceInfo)
            }
        }
        return result
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}