import com.sonder.myweatherapp.MwaBuildType

plugins {
    id("myweatherapp.android.application")
    id("myweatherapp.android.application.jacoco")
    id("myweatherapp.android.hilt")
    id("jacoco")
}

android {
    defaultConfig {
        applicationId = "com.sonder.myweatherapp"
        versionCode = 1
        versionName = "0.0.1"

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        val debug by getting {
            applicationIdSuffix = MwaBuildType.DEBUG.applicationIdSuffix
        }
        val release by getting {
            isMinifyEnabled = true
            applicationIdSuffix = MwaBuildType.RELEASE.applicationIdSuffix
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    viewBinding {
        enable = true
    }

    namespace = "com.sonder.myweatherapp"
}

dependencies {
    implementation(project(":feature:weather-list"))

    implementation(project(":core:common"))
    implementation(project(":core:data"))
    implementation(project(":core:model"))

    androidTestImplementation(project(":core:network"))
    androidTestImplementation(kotlin("test"))

    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui)
    implementation(libs.material)
}
