package com.example.cryptoreview.domain

import javax.inject.Inject

data class CoinInfoEntity @Inject constructor(
    val fromSymbol: String,
    val toSymbol: String?,
    val price: Double?,
    val lastUpdate: String,
    val highDay: Double?,
    val lowDay: Double?,
    val lastMarket: String?,
    val imageUrl: String
)