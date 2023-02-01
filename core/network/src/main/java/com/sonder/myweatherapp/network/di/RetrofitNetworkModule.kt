package com.sonder.myweatherapp.network.di

import com.sonder.myweatherapp.network.MwaNetworkDataSource
import com.sonder.myweatherapp.network.retrofit.RetrofitMwaNetwork
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RetrofitNetworkModule {
    @Binds
    fun RetrofitMwaNetwork.binds(): MwaNetworkDataSource
}
