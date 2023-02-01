plugins {
    id("myweatherapp.android.feature")
    id("myweatherapp.android.library.jacoco")
}

android {
    namespace = "com.sonder.myweatherapp.feature.weather_list"

    viewBinding {
        enable = true
    }
}
