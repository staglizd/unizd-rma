package org.unizd.rma.putopis.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.unizd.rma.putopis.domain.model.City
import org.unizd.rma.putopis.domain.usecase.GetCityUseCase

data class CityDetailUiState(
    val city: City? = null,
    val loading: Boolean = true,
)

@HiltViewModel(assistedFactory = CityDetailViewModel.Factory::class)
class CityDetailViewModel @AssistedInject constructor(
    @Assisted private val id: Int,
    private val getCity: GetCityUseCase,
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(id: Int): CityDetailViewModel
    }

    private val _state = MutableStateFlow(CityDetailUiState())
    val state: StateFlow<CityDetailUiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.value = CityDetailUiState(city = getCity(id), loading = false)
        }
    }
}