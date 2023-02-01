package com.sonder.myweatherapp.network.model

@kotlinx.serialization.Serializable
data class CityResponse(
    val id: Long,
    val name: String
)
