package org.unizd.rma.putopis.data.repository

import org.unizd.rma.putopis.data.mapper.toDomain
import org.unizd.rma.putopis.data.remote.WeatherApi
import org.unizd.rma.putopis.domain.model.City
import org.unizd.rma.putopis.domain.repository.CityRepository

/** Stvarna implementacija sučelja iz domain sloja: podaci s interneta. */
class CityRepositoryImpl(private val api: WeatherApi) : CityRepository {

    private class Location(val id: Int, val name: String, val region: String, val lat: Double, val lon: Double)

    private val locations = listOf(
        Location(1, "Zadar", "Zadarska", 44.12, 15.23),
        Location(2, "Split", "Splitsko-dalmatinska", 43.51, 16.44),
        Location(3, "Rijeka", "Primorsko-goranska", 45.33, 14.44),
        Location(4, "Osijek", "Osječko-baranjska", 45.55, 18.69),
        Location(5, "Dubrovnik", "Dubrovačko-neretvanska", 42.65, 18.09),
    )
    private var cache: List<City> = emptyList()

    override suspend fun getCities(): List<City> {
        val response = api.getForecasts(
            latitudes = locations.joinToString(",") { it.lat.toString() },
            longitudes = locations.joinToString(",") { it.lon.toString() },
        )
        cache = response.mapIndexed { i, dto ->
            val l = locations[i]
            dto.toDomain(l.id, l.name, l.region)
        }
        return cache
    }

    override suspend fun getCity(id: Int): City? {
        if (cache.isEmpty()) getCities() // npr. nakon smrti procesa
        return cache.firstOrNull { it.id == id }
    }
}
