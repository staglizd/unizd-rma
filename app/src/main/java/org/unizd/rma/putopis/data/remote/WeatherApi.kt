package org.unizd.rma.putopis.data.remote

import org.unizd.rma.putopis.data.remote.dto.ForecastDto
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    /** S više lokacija Open-Meteo vraća JSON niz (s jednom lokacijom vraća objekt). */
    @GET("v1/forecast")
    suspend fun getForecasts(
        @Query("latitude") latitudes: String,
        @Query("longitude") longitudes: String,
        @Query("current") current: String = "temperature_2m,relative_humidity_2m,wind_speed_10m,weather_code",
        @Query("daily") daily: String = "temperature_2m_max,temperature_2m_min",
        @Query("timezone") timezone: String = "Europe/Zagreb",
    ): List<ForecastDto>
}
