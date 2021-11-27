package com.example.cryptoreview.data.mappers

import com.example.cryptoreview.data.database.CoinInfoDbModel
import com.example.cryptoreview.data.network.model.CoinInfoDto
import com.example.cryptoreview.data.network.model.CoinInfoJsonContainerDto
import com.example.cryptoreview.data.network.model.CoinNamesListDto
import com.example.cryptoreview.domain.CoinInfoEntity
import com.google.gson.Gson
import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

class CoinMapper {
    fun mapDtoToDbModel(dto: CoinInfoDto) = CoinInfoDbModel(
        fromSymbol = dto.fromSymbol,
        toSymbol = dto.toSymbol,
        price = dto.price,
        lastUpdate = dto.lastUpdate,
        highDay = dto.highDay,
        lowDay = dto.lowDay,
        lastMarket = dto.lastMarket,
        imageUrl = BASE_IMAGE_URL + dto.imageUrl
    )

    fun mapJsonContainerToList(json: CoinInfoJsonContainerDto): List<CoinInfoDto> {
        val result = mutableListOf<CoinInfoDto>()
        val jsonObject = json.json ?: return result
        val coinKeySet = jsonObject.keySet()
        for (coinKey in coinKeySet) {
            val currencyJson = jsonObject.getAsJsonObject(coinKey)
            val currencyKeySet = currencyJson.keySet()
            for (currencyKey in currencyKeySet) {
                val priceInfo = Gson().fromJson(
                    currencyJson.getAsJsonObject(currencyKey),
                    CoinInfoDto::class.java
                )
                result.add(priceInfo)
            }
        }
        return result
    }

    fun mapNamesListToString(namesListDto: CoinNamesListDto): String {
        return namesListDto.names?.map {
            it.coinName?.name
        }?.joinToString(",") ?: ""
    }

    fun mapDbModelToEntity(dbModel: CoinInfoDbModel) = CoinInfoEntity(
        fromSymbol = dbModel.fromSymbol,
        toSymbol = dbModel.toSymbol,
        price = cutPriceLength(dbModel.price, 7),
        lastUpdate = convertTimestampToTime(dbModel.lastUpdate),
        highDay = cutPriceLength(dbModel.highDay, 12),
        lowDay = cutPriceLength(dbModel.lowDay, 12),
        lastMarket = dbModel.lastMarket,
        imageUrl = dbModel.imageUrl
    )

    private fun convertTimestampToTime(timestamp: Long?): String {
        if (timestamp == null) return ""
        val stamp = Timestamp(timestamp * 1000)
        val date = Date(stamp.time)
        val pattern = "HH:mm:ss"
        val simpleDateFormat = SimpleDateFormat(pattern, Locale.getDefault())
        simpleDateFormat.timeZone = TimeZone.getDefault()
        return simpleDateFormat.format(date)
    }

    private fun cutPriceLength(price: Double?, numbersAfterDot: Int): Double? {
        val format = "%.${numbersAfterDot}f"
        val priceAsString = String.format(format, price)
        return priceAsString.toDoubleOrNull()
    }

    companion object {
        private const val BASE_IMAGE_URL = "https://cryptocompare.com"
    }
}