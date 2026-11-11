package org.unizd.rma.putopis.presentation.cities

import org.unizd.rma.putopis.domain.model.City

/** Sva stanja u kojima ekran sa gradovima može biti. */
sealed interface CitiesUiState {
    data object Loading : CitiesUiState
    data class Success(val cities: List<City>) : CitiesUiState
    data class Error(val message: String) : CitiesUiState
}