package com.example.cryptoreview.presentation

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.cryptoreview.data.repository.CoinRepositoryImpl
import com.example.cryptoreview.domain.CoinRepository
import com.example.cryptoreview.domain.GetCoinInfoListUseCase
import com.example.cryptoreview.domain.GetCoinInfoUseCase
import com.example.cryptoreview.domain.LoadDataUseCase
import javax.inject.Inject

class CoinViewModel @Inject constructor(
    private val getCoinInfoListUseCase: GetCoinInfoListUseCase,
    private val getCoinInfoUseCase: GetCoinInfoUseCase,
    private val loadDataUseCase: LoadDataUseCase
) : ViewModel() {

    val coinInfoList = getCoinInfoListUseCase()

    fun getDetailInfo(fSym: String) = getCoinInfoUseCase(fSym)

    init {
        loadDataUseCase()
    }


}