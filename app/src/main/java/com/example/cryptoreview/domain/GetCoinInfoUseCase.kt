package com.example.cryptoreview.domain

class GetCoinInfoUseCase(private val repository: CoinRepository) {
    operator fun invoke(fromSymbols: String) = repository.getCoinInfo(fromSymbols)
}