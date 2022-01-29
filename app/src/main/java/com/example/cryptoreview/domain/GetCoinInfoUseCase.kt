package com.example.cryptoreview.domain

import javax.inject.Inject

class GetCoinInfoUseCase @Inject constructor(private val repository: CoinRepository) {
    operator fun invoke(fromSymbols: String) = repository.getCoinInfo(fromSymbols)
}