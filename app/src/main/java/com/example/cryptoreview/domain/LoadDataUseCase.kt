package com.example.cryptoreview.domain

class LoadDataUseCase(private val repository: CoinRepository) {
    operator fun invoke() = repository.loadData()
}