package org.unizd.rma.putopis.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.unizd.rma.putopis.BuildConfig
import org.unizd.rma.putopis.data.remote.WeatherApi
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

/** "Recepti" za objekte koje Hilt ne zna sam stvoriti (biblioteke). */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideJson(): Json = Json { ignoreUnknownKeys = true }

    @Provides
    @Singleton
    fun provideOkHttp(): OkHttpClient = OkHttpClient.Builder()
        .apply {
            // API ključ (ako postoji) dodaje se svakom zahtjevu na jednom mjestu
            if (BuildConfig.API_KEY.isNotBlank()) {
                addInterceptor { chain ->
                    val url = chain.request().url.newBuilder()
                        .addQueryParameter("apikey", BuildConfig.API_KEY)
                        .build()
                    chain.proceed(chain.request().newBuilder().url(url).build())
                }
            }
            if (BuildConfig.DEBUG) {
                addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
            }
        }
        .build()

    @Provides
    @Singleton
    fun provideWeatherApi(client: OkHttpClient, json: Json): WeatherApi =
        Retrofit.Builder()
            .baseUrl("https://api.open-meteo.com/")
            .client(client)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(WeatherApi::class.java)
}
