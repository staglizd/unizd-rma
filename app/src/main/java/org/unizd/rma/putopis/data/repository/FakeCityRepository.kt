package org.unizd.rma.putopis.data.repository

import kotlinx.coroutines.delay
import org.unizd.rma.putopis.domain.model.City
import org.unizd.rma.putopis.domain.repository.CityRepository

/** Lažni izvor podataka u memoriji. U V4 ga zamjenjuje stvarni REST poziv. */
class FakeCityRepository : CityRepository {

    private val cities = listOf(
        City(1, "Zadar", "Zadarska", 21),
        City(2, "Split", "Splitsko-dalmatinska", 24),
        City(3, "Rijeka", "Primorsko-goranska", 17),
        City(4, "Osijek", "Osječko-baranjska", 14),
        City(5, "Dubrovnik", "Dubrovačko-neretvanska", 25),
    )

    override suspend fun getCities(): List<City> {
        delay(800) // simulacija mreže: da vidimo stanje Loading
        return cities
    }

    override suspend fun getCity(id: Int): City? = cities.firstOrNull { it.id == id }
}