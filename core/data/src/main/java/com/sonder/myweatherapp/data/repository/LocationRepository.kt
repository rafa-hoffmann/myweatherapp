package com.sonder.myweatherapp.data.repository

import android.location.Location
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    suspend fun getCurrentLocation(): Flow<Location>
}
