package com.sonder.myweatherapp.data.di

import com.sonder.myweatherapp.data.repository.LocationRepository
import com.sonder.myweatherapp.data.repository.LocationRepositoryImpl
import com.sonder.myweatherapp.data.repository.WeatherRepository
import com.sonder.myweatherapp.data.repository.WeatherRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindWeatherRepository(
        weatherRepository: WeatherRepositoryImpl
    ): WeatherRepository

    @Binds
    fun bindLocationRepository(
        locationRepository: LocationRepositoryImpl
    ): LocationRepository
}
