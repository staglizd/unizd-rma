package org.unizd.rma.putopis.presentation.cities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.unizd.rma.putopis.domain.repository.CityRepository

class CitiesViewModel(private val repository: CityRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<CitiesUiState>(CitiesUiState.Loading)
    val uiState: StateFlow<CitiesUiState> = _uiState.asStateFlow()

    init {
        load()
    }

    fun load() {
        viewModelScope.launch {
            _uiState.value = CitiesUiState.Loading
            _uiState.value = try {
                CitiesUiState.Success(repository.getCities())
            } catch (e: Exception) {
                CitiesUiState.Error(e.message ?: "Nepoznata greška")
            }
        }
    }
}