package com.example.cryptoreview.screens

import android.os.Bundle
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
                startActivity(CoinDetailActivity.newIntent(this@CoinPriceListActivity, coinPriceInfo.fromsymbol))
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