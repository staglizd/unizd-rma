package org.unizd.rma.putopis.domain.usecase

import javax.inject.Inject
import org.unizd.rma.putopis.domain.model.City
import org.unizd.rma.putopis.domain.repository.CityRepository

class GetCityUseCase @Inject constructor(
    private val repository: CityRepository,
) {
    suspend operator fun invoke(id: Int): City? = repository.getCity(id)
}
