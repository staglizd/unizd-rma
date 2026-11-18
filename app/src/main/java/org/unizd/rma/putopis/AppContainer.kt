package org.unizd.rma.putopis

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.unizd.rma.putopis.data.remote.WeatherApi
import org.unizd.rma.putopis.data.repository.CityRepositoryImpl
import org.unizd.rma.putopis.domain.repository.CityRepository
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

/** Ručni DI: jedno mjesto koje stvara i spaja objekte. U V5 ga zamjenjuje Hilt. */
class AppContainer {
    private val json = Json { ignoreUnknownKeys = true }

    private val okHttp = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC })
        .build()

    private val api: WeatherApi = Retrofit.Builder()
        .baseUrl("https://api.open-meteo.com/")
        .client(okHttp)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()
        .create(WeatherApi::class.java)

    val cityRepository: CityRepository = CityRepositoryImpl(api)
}
