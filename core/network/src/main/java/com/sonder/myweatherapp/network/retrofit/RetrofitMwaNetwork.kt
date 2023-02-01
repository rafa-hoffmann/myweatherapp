package com.sonder.myweatherapp.network.retrofit

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.sonder.myweatherapp.core.network.BuildConfig
import com.sonder.myweatherapp.network.MwaNetworkDataSource
import com.sonder.myweatherapp.network.model.WeatherResponse
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

private interface RetrofitMwaNetworkApi {
    @GET(value = "weather")
    suspend fun getWeather(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("units") units: String
    ): WeatherResponse

    @GET(value = "weather")
    suspend fun getWeather(
        @Query("q") cityName: String,
        @Query("units") units: String
    ): WeatherResponse
}

private const val MwaBaseUrl = BuildConfig.BACKEND_URL
private const val MwaApiKey = BuildConfig.API_KEY

@Singleton
class RetrofitMwaNetwork @Inject constructor(
    networkJson: Json
) : MwaNetworkDataSource {

    private val networkApi = Retrofit.Builder()
        .baseUrl(MwaBaseUrl)
        .client(
            OkHttpClient.Builder()
                .addNetworkInterceptor(
                    Interceptor { chain ->
                        with(chain.request()) {
                            val url = url.newBuilder().addQueryParameter("appId", MwaApiKey).build()
                            chain.proceed(newBuilder().url(url).build())
                        }
                    }
                ).addInterceptor(
                    HttpLoggingInterceptor().apply {
                        setLevel(HttpLoggingInterceptor.Level.BODY)
                    }
                ).build()
        ).addConverterFactory(
            @OptIn(ExperimentalSerializationApi::class)
            networkJson.asConverterFactory("application/json".toMediaType())
        )
        .build()
        .create(RetrofitMwaNetworkApi::class.java)

    override suspend fun getWeather(lat: String, lon: String, units: String): WeatherResponse =
        networkApi.getWeather(lat = lat, lon = lon, units = units)

    override suspend fun getWeather(cityName: String, units: String): WeatherResponse =
        networkApi.getWeather(cityName = cityName, units = units)
}
