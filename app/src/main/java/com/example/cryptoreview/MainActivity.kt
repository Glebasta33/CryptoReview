package com.example.cryptoreview

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

class MainActivity : AppCompatActivity() {

    private lateinit var textView: TextView
    private lateinit var viewModel: CoinViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView = findViewById(R.id.textView)
        try {
            viewModel = ViewModelProviders.of(this).get(CoinViewModel()::class.java)
            viewModel.createDatabase(application)
            viewModel.liveDataPriceList.observe(this, {
                textView.text = it.toString()
            })
            viewModel.getDetailInfo("BTC").observe(this, {
                textView.text = it.toString()
            })
        } catch(e: Exception) {
            textView.text = e.message
        }

    }

}