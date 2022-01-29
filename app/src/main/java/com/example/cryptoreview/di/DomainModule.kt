package com.example.cryptoreview.di

import com.example.cryptoreview.data.repository.CoinRepositoryImpl
import com.example.cryptoreview.domain.CoinRepository
import dagger.Binds
import dagger.Module

@Module
interface DomainModule {
    @Binds
    fun bindRepository(impl: CoinRepositoryImpl): CoinRepository
}