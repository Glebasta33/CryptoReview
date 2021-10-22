package com.example.cryptoreview.screens

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.cryptoreview.CoinViewModel
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
    private lateinit var tvLastTrade: TextView
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
        tvLastTrade = findViewById(R.id.tvLastTrade)
        tvLastUpdate = findViewById(R.id.tvLastUpdate)

        if (!intent.hasExtra(EXTRA_FROM_SYMBOL)) {
            finish()
            return
        }
        val fromSymbol = intent.getStringExtra(EXTRA_FROM_SYMBOL)
        viewModel = ViewModelProviders.of(this).get(CoinViewModel()::class.java)
        viewModel.createDatabase(application)
        viewModel.getDetailInfo(fromSymbol ?: "BTC").observe(this, {
            Picasso.get().load(it.getFullImageUrl()).into(ivLogoCoin)
            tvFromSymbols.text = it.fromsymbol
            tvToSymbols.text = it.tosymbol
            tvPrice.text = it.price.toString()
            tvMinPrice.text = it.highday.toString()
            tvMaxPrice.text = it.lowday.toString()
            tvLastTrade.text = it.market
            tvLastUpdate.text = it.getFormattedTime()
        })
    }

    companion object {
        const val EXTRA_FROM_SYMBOL = "fSymbol"

        fun newIntent(context: Context, fromSymbol: String): Intent {
            val intent = Intent(context, CoinDetailActivity::class.java)
            intent.putExtra(EXTRA_FROM_SYMBOL, fromSymbol)
            return intent
        }
    }
}