package org.unizd.rma.putopis.domain.usecase

import javax.inject.Inject
import org.unizd.rma.putopis.domain.model.City
import org.unizd.rma.putopis.domain.repository.CityRepository

/** Jedna poslovna radnja: dohvati gradove, sortirane po imenu. */
class GetCitiesUseCase @Inject constructor(
    private val repository: CityRepository,
) {
    suspend operator fun invoke(): List<City> =
        repository.getCities().sortedBy { it.name }
}
