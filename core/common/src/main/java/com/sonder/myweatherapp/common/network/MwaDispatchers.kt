package com.sonder.myweatherapp.common.network

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val mwaDispatcher: MwaDispatchers)

enum class MwaDispatchers {
    IO
}
