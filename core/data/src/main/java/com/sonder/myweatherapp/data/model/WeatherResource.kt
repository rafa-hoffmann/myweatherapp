package com.sonder.myweatherapp.data.model

import com.sonder.myweatherapp.model.data.MainWeatherResource
import com.sonder.myweatherapp.model.data.PrecipitationResource
import com.sonder.myweatherapp.model.data.WeatherDataResource
import com.sonder.myweatherapp.model.data.WeatherResource
import com.sonder.myweatherapp.model.data.WindResource
import com.sonder.myweatherapp.network.model.WeatherDataResponse
import com.sonder.myweatherapp.network.model.WeatherResponse
import kotlinx.datetime.Instant

fun WeatherResponse.asResource(isCustomLocation: Boolean) = WeatherResource(
    weather = weather.first().asResource(),
    main = with(main) {
        MainWeatherResource(
            temp = temp,
            feelsLike = feelsLike,
            tempMin = tempMin,
            tempMax = tempMax,
            pressure = pressure,
            humidity = humidity
        )
    },
    wind = WindResource(speed = wind.speed),
    rain = PrecipitationResource(rain?.lastHour, rain?.lastThreeHours),
    snow = PrecipitationResource(snow?.lastHour, snow?.lastThreeHours),
    dt = Instant.fromEpochMilliseconds(dt),
    id = id,
    name = name,
    isCustomLocation = isCustomLocation
)

private fun WeatherDataResponse.asResource() =
    WeatherDataResource(
        main = main,
        description = description,
        icon = icon
    )
