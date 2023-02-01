package com.sonder.myweatherapp

@Suppress("unused")
enum class MwaBuildType(val applicationIdSuffix: String? = null) {
    DEBUG(".debug"),
    RELEASE
}
