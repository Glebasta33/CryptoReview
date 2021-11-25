package com.example.cryptoreview.data.network.model

import android.media.Rating
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CoinNameDto (
    @SerializedName("Name")
    @Expose
    val name: String? = null,
)