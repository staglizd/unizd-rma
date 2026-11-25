package org.unizd.rma.putopis.presentation.cities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject
import org.unizd.rma.putopis.domain.usecase.GetCitiesUseCase
import retrofit2.HttpException

@HiltViewModel
class CitiesViewModel @Inject constructor(
    private val getCities: GetCitiesUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<CitiesUiState>(CitiesUiState.Loading)
    val uiState: StateFlow<CitiesUiState> = _uiState.asStateFlow()

    init {
        load()
    }

    fun load() {
        viewModelScope.launch {
            _uiState.value = CitiesUiState.Loading
            _uiState.value = try {
                CitiesUiState.Success(getCities())
            } catch (e: IOException) {
                CitiesUiState.Error("Nema veze s internetom")
            } catch (e: HttpException) {
                CitiesUiState.Error("Server je vratio ${e.code()}")
            } catch (e: Exception) {
                CitiesUiState.Error(e.message ?: "Nepoznata greška")
            }
        }
    }
}