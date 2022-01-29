package com.example.cryptoreview.di

import androidx.lifecycle.ViewModel
import com.example.cryptoreview.presentation.CoinViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {
    @IntoMap
    @ViewModelKey(CoinViewModel::class)
    @Binds
    fun bindsCoinViewModel(impl: CoinViewModel): ViewModel
}