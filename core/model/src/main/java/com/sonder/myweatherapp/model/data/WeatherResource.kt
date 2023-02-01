package com.sonder.myweatherapp.model.data

import kotlinx.datetime.Instant

data class WeatherResource(
    val weather: WeatherDataResource,
    val main: MainWeatherResource,
    val wind: WindResource,
    val rain: PrecipitationResource?,
    val snow: PrecipitationResource?,
    val dt: Instant,
    val id: Long,
    val name: String,
    val isCustomLocation: Boolean
)

data class WeatherDataResource(
    val main: String,
    val description: String,
    val icon: String
)

data class MainWeatherResource(
    val temp: Double,
    val feelsLike: Double,
    val tempMin: Double,
    val tempMax: Double,
    val pressure: Int,
    val humidity: Int
)

data class WindResource(
    val speed: Double
)

data class PrecipitationResource(
    val lastHour: Double?,
    val lastThreeHours: Double?
)