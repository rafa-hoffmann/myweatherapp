package com.sonder.myweatherapp.network

import com.sonder.myweatherapp.network.model.WeatherResponse

interface MwaNetworkDataSource {

    suspend fun getWeather(lat: String, lon: String, units: String): WeatherResponse

    suspend fun getWeather(cityName: String, units: String): WeatherResponse
}
