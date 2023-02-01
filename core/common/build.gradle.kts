plugins {
    id("myweatherapp.android.library")
    id("myweatherapp.android.library.jacoco")
    id("myweatherapp.android.hilt")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

android {
    buildFeatures {
        buildConfig = true
    }
    namespace = "com.sonder.myweatherapp.core.common"
}

secrets {
    defaultPropertiesFileName = "secrets.defaults.properties"
}

dependencies {
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.glide)
    annotationProcessor(libs.glide.compiler)
}
