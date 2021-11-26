package com.example.cryptoreview.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.cryptoreview.R
import com.squareup.picasso.Picasso

class CoinDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: CoinViewModel
    private lateinit var ivLogoCoin: ImageView
    private lateinit var tvFromSymbols: TextView
    private lateinit var tvToSymbols: TextView
    private lateinit var tvPrice: TextView
    private lateinit var tvMinPrice: TextView
    private lateinit var tvMaxPrice: TextView
    private lateinit var tvLastMarket: TextView
    private lateinit var tvLastUpdate: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_detail)
        ivLogoCoin = findViewById(R.id.ivLogoCoin)
        tvFromSymbols = findViewById(R.id.tvFromSymbols)
        tvToSymbols = findViewById(R.id.tvToSymbols)
        tvPrice = findViewById(R.id.tvPrice)
        tvMinPrice = findViewById(R.id.tvMinPrice)
        tvMaxPrice = findViewById(R.id.tvMaxPrice)
        tvLastMarket = findViewById(R.id.tvLastTrade)
        tvLastUpdate = findViewById(R.id.tvLastUpdate)

        if (!intent.hasExtra(FROM_SYMBOL)) {
            finish()
            return
        }
        val fromSymbol = intent.getStringExtra(FROM_SYMBOL) ?: EMPTY_SYMBOL
        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]
        viewModel.getDetailInfo(fromSymbol).observe(this) {
            tvFromSymbols.text = it.fromSymbol
            tvToSymbols.text = it.toSymbol
            tvPrice.text = it.price.toString()
            tvMinPrice.text = it.lowDay.toString()
            tvMaxPrice.text = it.highDay.toString()
            tvLastMarket.text = it.lastMarket
            tvLastUpdate.text = it.lastUpdate
            Picasso.get().load(it.imageUrl).into(ivLogoCoin)
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