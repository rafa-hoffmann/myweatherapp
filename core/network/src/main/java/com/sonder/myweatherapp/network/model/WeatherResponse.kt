package com.sonder.myweatherapp.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponse(
    val coord: CoordinateResponse,
    val weather: List<WeatherDataResponse>,
    val base: String,
    val main: MainWeatherResponse,
    val visibility: Long,
    val wind: WindResponse,
    val rain: PrecipitationResponse? = null,
    val snow: PrecipitationResponse? = null,
    val clouds: CloudResponse,
    val dt: Long,
    val timezone: Long,
    val id: Long,
    val name: String,
    val cod: Long
)

@Serializable
data class CoordinateResponse(
    val lon: Double,
    val lat: Double
)

@Serializable
data class WeatherDataResponse(
    val id: Long,
    val main: String,
    val description: String,
    val icon: String
)

@Serializable
data class MainWeatherResponse(
    val temp: Double,
    @SerialName("feels_like")
    val feelsLike: Double,
    @SerialName("temp_min")
    val tempMin: Double,
    @SerialName("temp_max")
    val tempMax: Double,
    val pressure: Int,
    val humidity: Int
)

@Serializable
data class WindResponse(
    val speed: Double,
    val deg: Int,
    val gust: Double? = null
)

@Serializable
data class PrecipitationResponse(
    @SerialName("1h")
    val lastHour: Double? = null,
    @SerialName("3h")
    val lastThreeHours: Double? = null
)

@Serializable
data class CloudResponse(
    val all: Int
)
