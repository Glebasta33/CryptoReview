package com.example.cryptoreview.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProviders
import com.example.cryptoreview.CoinViewModel
import com.example.cryptoreview.R

class CoinDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: CoinViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_detail)
        if (!intent.hasExtra(EXTRA_FROM_SYMBOL)) {
            finish()
            return
        }
        val fromSymbol = intent.getStringExtra(EXTRA_FROM_SYMBOL)
        viewModel = ViewModelProviders.of(this).get(CoinViewModel()::class.java)
        viewModel.createDatabase(application)
        viewModel.getDetailInfo(fromSymbol?:"BTC").observe(this, {
            Log.d("DETAIL_INFO_TEST", it.toString())
        })
    }

    companion object {
        const val EXTRA_FROM_SYMBOL = "fSymbol"
    }
}