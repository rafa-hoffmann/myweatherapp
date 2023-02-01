package com.sonder.myweatherapp.data.repository

import android.annotation.SuppressLint
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.sonder.myweatherapp.common.network.Dispatcher
import com.sonder.myweatherapp.common.network.MwaDispatchers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    @Dispatcher(MwaDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val fusedLocationProviderClient: FusedLocationProviderClient
) : LocationRepository {

    @SuppressLint("MissingPermission")
    override suspend fun getCurrentLocation() = flow {
        val cancellationTokenSource = CancellationTokenSource()
        val currentLocationTask = fusedLocationProviderClient.getCurrentLocation(
            Priority.PRIORITY_HIGH_ACCURACY,
            cancellationTokenSource.token
        )
        emit(currentLocationTask.await(cancellationTokenSource))
    }.flowOn(ioDispatcher)
}
