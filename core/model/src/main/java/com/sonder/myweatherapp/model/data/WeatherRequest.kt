package com.sonder.myweatherapp.model.data


sealed interface WeatherRequest {
    val unit: WeatherUnit

    data class Coordinates(
        val lon: String,
        val lat: String,
        override val unit: WeatherUnit = WeatherUnit.METRIC
    ) : WeatherRequest

    data class City(
        val cityName: String,
        override val unit: WeatherUnit = WeatherUnit.METRIC
    ) : WeatherRequest
}

enum class WeatherUnit(val apiName: String) {
    METRIC("metric"),
    IMPERIAL("imperial"),
    KELVIN("kelvin")
}