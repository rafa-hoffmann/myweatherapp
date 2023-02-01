package com.sonder.myweatherapp.network.local

import android.content.res.AssetManager
import com.sonder.myweatherapp.network.MwaLocalDataSource
import com.sonder.myweatherapp.network.model.CityResponse
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

private const val defaultCitiesFileName = "defaultCities.json"

@Singleton
class MwaLocalDataSourceImpl @Inject constructor(
    private val assetManager: AssetManager
) : MwaLocalDataSource {
    override suspend fun getDefaultCities(): List<CityResponse> {
        val citiesText =
            assetManager.open(defaultCitiesFileName).bufferedReader().use { it.readText() }

        return Json.decodeFromString(citiesText)
    }
}
