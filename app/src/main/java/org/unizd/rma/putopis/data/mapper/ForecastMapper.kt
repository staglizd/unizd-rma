package org.unizd.rma.putopis.data.mapper

import java.time.LocalDate
import org.unizd.rma.putopis.data.remote.dto.ForecastDto
import org.unizd.rma.putopis.domain.model.City
import org.unizd.rma.putopis.domain.model.DayForecast
import org.unizd.rma.putopis.domain.model.WeatherType

/** Čista funkcija DTO → domain. Jedino mjesto koje poznaje oba oblika. */
fun ForecastDto.toDomain(id: Int, name: String, region: String) = City(
    id = id,
    name = name,
    region = region,
    temp = current.temperature,
    humidity = current.humidity,
    windSpeed = current.windSpeed,
    weather = current.weatherCode.toWeatherType(),
    dailyForecast = daily.time.indices.map {
        DayForecast(LocalDate.parse(daily.time[it]), daily.minTemps[it], daily.maxTemps[it])
    },
)

/** WMO kodovi vremena → naš enum. */
fun Int.toWeatherType() = when (this) {
    0 -> WeatherType.CLEAR
    1, 2, 3, 45, 48 -> WeatherType.CLOUDY
    in 51..67, in 80..82 -> WeatherType.RAIN
    in 71..77, 85, 86 -> WeatherType.SNOW
    95, 96, 99 -> WeatherType.STORM
    else -> WeatherType.UNKNOWN
}
