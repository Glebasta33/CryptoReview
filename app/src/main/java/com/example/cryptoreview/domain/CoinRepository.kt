package com.example.cryptoreview.domain

import androidx.lifecycle.LiveData

interface CoinRepository {
    fun getCoinInfoList(): LiveData<List<CoinInfoEntity>>
    fun getCoinInfo(fromSymbol: String): LiveData<CoinInfoEntity>
    fun loadData()
}