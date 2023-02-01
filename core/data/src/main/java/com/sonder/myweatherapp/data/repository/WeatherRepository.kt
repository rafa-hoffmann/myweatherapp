package com.sonder.myweatherapp.data.repository

import com.sonder.myweatherapp.model.data.CityResource
import com.sonder.myweatherapp.model.data.WeatherRequest
import com.sonder.myweatherapp.model.data.WeatherResource
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    suspend fun getWeather(request: WeatherRequest): Flow<HashMap<CityName, WeatherResource>>

    suspend fun getDefaultCities(): Flow<List<CityResource>>
}
