package com.example.cryptoreview.di

import android.app.Application
import com.example.cryptoreview.presentation.CoinDetailFragment
import com.example.cryptoreview.presentation.CoinPriceListActivity
import com.example.cryptoreview.presentation.CryptoReviewApp
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [
    DomainModule::class,
    DataModule::class,
    ViewModelModule::class
])
interface ApplicationComponent {
    fun inject(instance: CoinPriceListActivity)
    fun inject(instance: CoinDetailFragment)
    fun inject(instance: CryptoReviewApp)

    @Component.Factory
    interface ApplicationComponentFactory {
        fun create(
            @BindsInstance
            application: Application
        ): ApplicationComponent
    }
}