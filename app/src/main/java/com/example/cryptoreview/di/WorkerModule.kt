package com.example.cryptoreview.di

import com.example.cryptoreview.data.workers.ChildWorkerFactory
import com.example.cryptoreview.data.workers.RefreshDataWorker
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface WorkerModule {

    @Binds
    @IntoMap
    @WorkerKey(RefreshDataWorker::class)
    fun bindRefreshWorkerFactory(worker: RefreshDataWorker.Factory): ChildWorkerFactory
}