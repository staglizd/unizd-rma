package org.unizd.rma.putopis.domain.model

import java.time.LocalDate

/** Grad s trenutnim vremenom i prognozom. Domain model: oblik koji treba NAŠOJ aplikaciji. */
data class City(
    val id: Int,
    val name: String,
    val region: String,
    val temp: Double,
    val humidity: Int,
    val windSpeed: Double,
    val weather: WeatherType,
    val dailyForecast: List<DayForecast>,
)

data class DayForecast(val date: LocalDate, val min: Double, val max: Double)

enum class WeatherType { CLEAR, CLOUDY, RAIN, SNOW, STORM, UNKNOWN }
