package org.unizd.rma.putopis.domain.repository


import org.unizd.rma.putopis.domain.model.City

/**
 * Izvor podataka o gradovima. Domain ne zna je li iza njega memorija, mreža ili baza.
 */
interface CityRepository {
    suspend fun getCities(): List<City>
    suspend fun getCity(id: Int): City?
}