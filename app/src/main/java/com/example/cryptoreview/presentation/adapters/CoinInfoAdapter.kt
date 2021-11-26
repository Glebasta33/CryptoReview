package com.example.cryptoreview.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoreview.R
import com.example.cryptoreview.databinding.ItemCoinInfoBinding
import com.example.cryptoreview.domain.CoinInfoEntity
import com.squareup.picasso.Picasso

class CoinInfoAdapter(private val context: Context) : RecyclerView.Adapter<CoinInfoViewHolder>() {

    var coinInfoList: List<CoinInfoEntity> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onCoinClickListener: OnCoinClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinInfoViewHolder {
        val binding = ItemCoinInfoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CoinInfoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CoinInfoViewHolder, position: Int) {
        val coin = coinInfoList[position]
        with(holder.binding) {
            val symbolsTemplate = context.resources.getString(R.string.symbols_template)
            val lastUpdateTemplate = context.resources.getString(R.string.last_update_template)
            tvSymbols.text = String.format(symbolsTemplate, coin.fromSymbol, coin.toSymbol)
            tvValue.text = coin.price.toString()
            tvLastUpdateTime.text = String.format(
                lastUpdateTemplate,
                coin.lastUpdate
            )
            Picasso.get().load(coin.imageUrl).into(ivLogoCoin)
            root.setOnClickListener {
                onCoinClickListener?.onCoinClick(coin)
            }
        }
    }

    override fun getItemCount() = coinInfoList.size

    interface OnCoinClickListener {
        fun onCoinClick(coinPriceInfo: CoinInfoEntity)
    }
}