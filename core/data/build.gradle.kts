plugins {
    id("myweatherapp.android.library")
    id("myweatherapp.android.library.jacoco")
    id("myweatherapp.android.hilt")
    id("kotlinx-serialization")
}

android {
    namespace = "com.sonder.myweatherapp.core.data"
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:model"))
    implementation(project(":core:network"))

    implementation(libs.androidx.core.ktx)

    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.play.services)
    implementation(libs.kotlinx.serialization.json)

    implementation(libs.play.services.location)
}
