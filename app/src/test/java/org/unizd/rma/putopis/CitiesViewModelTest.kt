package org.unizd.rma.putopis

import java.io.IOException
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.unizd.rma.putopis.domain.model.City
import org.unizd.rma.putopis.domain.model.WeatherType
import org.unizd.rma.putopis.domain.repository.CityRepository
import org.unizd.rma.putopis.domain.usecase.GetCitiesUseCase
import org.unizd.rma.putopis.presentation.cities.CitiesUiState
import org.unizd.rma.putopis.presentation.cities.CitiesViewModel

class CitiesViewModelTest {

    @get:Rule
    val mainRule = MainDispatcherRule()

    /** Lažni repozitorij: vraća što mu kažemo. Ne treba emulator ni mrežu. */
    private class FakeRepo(private val result: Result<List<City>>) : CityRepository {
        override suspend fun getCities() = result.getOrThrow()
        override suspend fun getCity(id: Int): City? = null
    }

    private fun city(id: Int, name: String) =
        City(id, name, "Regija", 20.0, 50, 5.0, WeatherType.CLEAR, emptyList())

    @Test
    fun `uspjeh - stanje je Success sa sortiranim gradovima`() {
        val repo = FakeRepo(Result.success(listOf(city(1, "Zadar"), city(2, "Osijek"))))
        val vm = CitiesViewModel(GetCitiesUseCase(repo))

        val state = vm.uiState.value
        assertTrue(state is CitiesUiState.Success)
        assertEquals(listOf("Osijek", "Zadar"), (state as CitiesUiState.Success).cities.map { it.name })
    }

    @Test
    fun `nema mreze - stanje je Error s porukom`() {
        val repo = FakeRepo(Result.failure(IOException("x")))
        val vm = CitiesViewModel(GetCitiesUseCase(repo))

        val state = vm.uiState.value
        assertTrue(state is CitiesUiState.Error)
        assertEquals("Nema veze s internetom", (state as CitiesUiState.Error).message)
    }
}
