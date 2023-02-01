package com.sonder.myweatherapp.network

import com.sonder.myweatherapp.network.model.CityResponse

interface MwaLocalDataSource {

    suspend fun getDefaultCities(): List<CityResponse>
}
