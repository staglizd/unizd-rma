package org.unizd.rma.putopis.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.unizd.rma.putopis.domain.model.City
import org.unizd.rma.putopis.domain.repository.CityRepository

data class CityDetailUiState(
    val city: City? = null,
    val loading: Boolean = true,
)

class CityDetailViewModel(
    private val id: Int,
    private val repository: CityRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(CityDetailUiState())
    val state: StateFlow<CityDetailUiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.value = CityDetailUiState(city = repository.getCity(id), loading = false)
        }
    }
}