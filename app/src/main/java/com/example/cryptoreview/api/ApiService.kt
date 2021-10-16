package com.example.cryptoreview.api

import com.example.cryptoreview.pojo.CoinInfoListOfData
import com.example.cryptoreview.pojo.CoinPriceInfoRawData
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("top/totalvolfull")
    fun getTopCoinsInfo(
        @Query(QUERY_PARAM_API_KEY) apiKey: String = "01ae3f98bd7ebde13518d002a3caf813b7c3238ed66a552d33a945efef4d4a72",
        @Query(QUERY_PARAM_LIMIT) limit: Int = 10,
        @Query(QUERY_PARAM_TO_SYMBOL) toSymbol: String = CURRENCY
    ): Single<CoinInfoListOfData>

    @GET("pricemultifull")
    fun getFullPriceList(
        @Query(QUERY_PARAM_API_KEY) apiKey: String = "01ae3f98bd7ebde13518d002a3caf813b7c3238ed66a552d33a945efef4d4a72",
        @Query(QUERY_PARAM_FROM_SYMBOLS) fromSybols: String,
        @Query(QUERY_PARAM_TO_SYMBOLS) toSybols: String = CURRENCY
    ): Single<CoinPriceInfoRawData>

    companion object {
        private const val QUERY_PARAM_API_KEY = "api_key"
        private const val QUERY_PARAM_LIMIT = "limit"
        private const val QUERY_PARAM_TO_SYMBOL = "tsym"
        private const val CURRENCY = "USD"

        private const val QUERY_PARAM_FROM_SYMBOLS = "fsyms"
        private const val QUERY_PARAM_TO_SYMBOLS = "tsyms"
    }
}