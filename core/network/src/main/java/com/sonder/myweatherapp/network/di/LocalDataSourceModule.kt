package com.sonder.myweatherapp.network.di

import com.sonder.myweatherapp.network.MwaLocalDataSource
import com.sonder.myweatherapp.network.local.MwaLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface LocalDataSourceModule {
    @Binds
    fun MwaLocalDataSourceImpl.binds(): MwaLocalDataSource
}
