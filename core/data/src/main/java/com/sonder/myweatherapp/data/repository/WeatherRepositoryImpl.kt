package com.sonder.myweatherapp.data.repository

import com.sonder.myweatherapp.common.network.Dispatcher
import com.sonder.myweatherapp.common.network.MwaDispatchers
import com.sonder.myweatherapp.data.model.asResource
import com.sonder.myweatherapp.model.data.CityResource
import com.sonder.myweatherapp.model.data.WeatherRequest
import com.sonder.myweatherapp.model.data.WeatherResource
import com.sonder.myweatherapp.network.local.MwaLocalDataSourceImpl
import com.sonder.myweatherapp.network.retrofit.RetrofitMwaNetwork
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    @Dispatcher(MwaDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val network: RetrofitMwaNetwork,
    private val local: MwaLocalDataSourceImpl
) : WeatherRepository {
    private val weatherResourceList = hashMapOf<CityName, WeatherResource>()
    private val defaultCityNameList = hashSetOf<CityName>()

    override suspend fun getWeather(request: WeatherRequest): Flow<HashMap<CityName, WeatherResource>> =
        flow {
            val weatherResponse = when (request) {
                is WeatherRequest.City -> network.getWeather(request.cityName, request.unit.apiName)
                is WeatherRequest.Coordinates -> network.getWeather(
                    request.lat,
                    request.lon,
                    request.unit.apiName
                )
            }
            weatherResourceList.getOrPut(weatherResponse.name) {
                weatherResponse.asResource(
                    isCustomLocation = !defaultCityNameList.contains(weatherResponse.name)
                )
            }
            emit(weatherResourceList)
        }.flowOn(ioDispatcher)

    override suspend fun getDefaultCities(): Flow<List<CityResource>> = flow {
        val defaultCities = local.getDefaultCities()
        defaultCityNameList.addAll(defaultCities.map { it.name })
        emit(defaultCities.asResource())
    }.flowOn(ioDispatcher)
}

typealias CityName = String
