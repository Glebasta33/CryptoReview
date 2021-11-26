package com.example.cryptoreview.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.cryptoreview.R
import com.example.cryptoreview.databinding.ActivityCoinDetailBinding
import com.squareup.picasso.Picasso

class CoinDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: CoinViewModel

    private val binding by lazy {
        ActivityCoinDetailBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        if (!intent.hasExtra(FROM_SYMBOL)) {
            finish()
            return
        }
        val fromSymbol = intent.getStringExtra(FROM_SYMBOL) ?: EMPTY_SYMBOL
        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]
        viewModel.getDetailInfo(fromSymbol).observe(this) {
            with(binding) {
                tvFromSymbols.text = it.fromSymbol
                tvToSymbols.text = it.toSymbol
                tvPrice.text = it.price.toString()
                tvMinPrice.text = it.lowDay.toString()
                tvMaxPrice.text = it.highDay.toString()
                tvLastTrade.text = it.lastMarket
                tvLastUpdate.text = it.lastUpdate
                Picasso.get().load(it.imageUrl).into(ivLogoCoin)
            }
        }

    }

    companion object {
        private const val FROM_SYMBOL = "fSymbol"
        private const val EMPTY_SYMBOL = ""

        fun newIntent(context: Context, fromSymbol: String): Intent {
            val intent = Intent(context, CoinDetailActivity::class.java)
            intent.putExtra(FROM_SYMBOL, fromSymbol)
            return intent
        }
    }

}