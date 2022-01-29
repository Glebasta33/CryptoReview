package com.example.cryptoreview.presentation

import android.app.Application
import com.example.cryptoreview.di.DaggerApplicationComponent

class CryptoReviewApp : Application() {
    val component by lazy {
        DaggerApplicationComponent.factory()
            .create(this)
    }
}