package com.example.cryptoreview.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoreview.R
import com.example.cryptoreview.databinding.ActivityCoinPriceListBinding
import com.example.cryptoreview.presentation.adapters.CoinInfoAdapter

import com.example.cryptoreview.domain.CoinInfoEntity
import javax.inject.Inject

class CoinPriceListActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: CoinViewModel
    private val binding by lazy {
        ActivityCoinPriceListBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as CryptoReviewApp).component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val adapter = CoinInfoAdapter(this)
        adapter.onCoinClickListener = object : CoinInfoAdapter.OnCoinClickListener {
            override fun onCoinClick(coinInfo: CoinInfoEntity) {
                if (binding.fragmentContainer == null) {
                    launchDetailActivity(coinInfo.fromSymbol)
                } else {
                    launchDetailFragment(coinInfo.fromSymbol)
                }
            }
        }
        binding.rvCoinPriceList.adapter = adapter
        viewModel = ViewModelProvider(this, viewModelFactory)[CoinViewModel::class.java]
        viewModel.coinInfoList.observe(this) {
            adapter.submitList(it)
        }
    }

    private fun launchDetailActivity(fromSymbol: String) {
        startActivity(CoinDetailActivity.newIntent(this@CoinPriceListActivity, fromSymbol))
    }

    private fun launchDetailFragment(fromSymbol: String) {
        supportFragmentManager.popBackStack() // delete previous
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, CoinDetailFragment.newInstance(fromSymbol))
            .addToBackStack(null)
            .commit()
    }
}