package com.example.cryptoreview.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptoreview.data.network.ApiFactory
import com.example.cryptoreview.data.database.AppDatabase
import com.example.cryptoreview.data.network.model.CoinInfoDto
import com.example.cryptoreview.data.network.model.CoinInfoJsonContainerDto
import com.example.cryptoreview.data.repository.CoinRepositoryImpl
import com.example.cryptoreview.domain.GetCoinInfoListUseCase
import com.example.cryptoreview.domain.GetCoinInfoUseCase
import com.example.cryptoreview.domain.LoadDataUseCase
import com.google.gson.Gson
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class CoinViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = CoinRepositoryImpl(application)

    private val getCoinInfoListUseCase = GetCoinInfoListUseCase(repository)
    private val getCoinInfoUseCase = GetCoinInfoUseCase(repository)
    private val loadDataUseCase = LoadDataUseCase(repository)

    val coinInfoList = getCoinInfoListUseCase()

    fun getDetailInfo(fSym: String) = getCoinInfoUseCase(fSym)

    init {
        viewModelScope.launch {
            loadDataUseCase()
        }
    }


}