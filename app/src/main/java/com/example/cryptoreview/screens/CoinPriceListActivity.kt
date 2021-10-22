package com.example.cryptoreview.screens

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoreview.CoinViewModel
import com.example.cryptoreview.R
import com.example.cryptoreview.adapter.CoinInfoAdapter
import com.example.cryptoreview.pojo.CoinPriceInfo

class CoinPriceListActivity : AppCompatActivity() {

    private lateinit var viewModel: CoinViewModel
    private lateinit var rvCoinPriceList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_price_list)
        rvCoinPriceList = findViewById(R.id.rvCoinPriceList)
        val adapter = CoinInfoAdapter(this)
        adapter.onCoinClickListener = object : CoinInfoAdapter.OnCoinClickListener {
            override fun onCoinClick(coinPriceInfo: CoinPriceInfo) {
                val intent = Intent(this@CoinPriceListActivity, CoinDetailActivity::class.java)
                intent.putExtra(CoinDetailActivity.EXTRA_FROM_SYMBOL, "BTC")
                startActivity(intent)
            }
        }
        rvCoinPriceList.adapter = adapter
        viewModel = ViewModelProviders.of(this).get(CoinViewModel()::class.java)
        viewModel.createDatabase(application)
        viewModel.liveDataPriceList.observe(this, {
            adapter.coinInfoList = it
        })

    }
}