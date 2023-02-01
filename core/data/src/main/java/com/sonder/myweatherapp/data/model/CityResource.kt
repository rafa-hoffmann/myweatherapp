package com.sonder.myweatherapp.data.model

import com.sonder.myweatherapp.model.data.CityResource
import com.sonder.myweatherapp.network.model.CityResponse

fun List<CityResponse>.asResource() = map {
    CityResource(
        id = it.id,
        name = it.name
    )
}
