package com.example.cryptoreview.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoreview.R
import com.example.cryptoreview.presentation.adapters.CoinInfoAdapter

import com.example.cryptoreview.domain.CoinInfoEntity

class CoinPriceListActivity : AppCompatActivity() {

    private lateinit var viewModel: CoinViewModel
    private lateinit var rvCoinPriceList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_price_list)
        rvCoinPriceList = findViewById(R.id.rvCoinPriceList)
        val adapter = CoinInfoAdapter(this)
        adapter.onCoinClickListener = object : CoinInfoAdapter.OnCoinClickListener {
            override fun onCoinClick(coinInfo: CoinInfoEntity) {
                startActivity(
                    CoinDetailActivity.newIntent(
                        this@CoinPriceListActivity,
                        coinInfo.fromSymbol
                    )
                )
            }
        }
        rvCoinPriceList.adapter = adapter
        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]
        viewModel.coinInfoList.observe(this) {
            adapter.coinInfoList = it
        }

    }
}