package org.unizd.rma.putopis.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** DTO = oblik JSON-a kakav ga šalje server. Imena i tipovi prate TUĐI API. */
@Serializable
data class ForecastDto(
    val latitude: Double,
    val longitude: Double,
    val current: CurrentDto,
    val daily: DailyDto,
)

@Serializable
data class CurrentDto(
    @SerialName("temperature_2m") val temperature: Double,
    @SerialName("relative_humidity_2m") val humidity: Int,
    @SerialName("wind_speed_10m") val windSpeed: Double,
    @SerialName("weather_code") val weatherCode: Int,
)

@Serializable
data class DailyDto(
    val time: List<String>,
    @SerialName("temperature_2m_max") val maxTemps: List<Double>,
    @SerialName("temperature_2m_min") val minTemps: List<Double>,
)
