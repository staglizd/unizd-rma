package org.unizd.rma.putopis.presentation.cities

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import org.unizd.rma.putopis.domain.model.City
import org.unizd.rma.putopis.domain.model.WeatherType
import org.unizd.rma.putopis.ui.theme.PutopisTheme

/** Ulazna točka: zna za ViewModel. Ekran ispod ne zna ništa o njemu. */
@Composable
fun CitiesRoute(
    onCityClick: (Int) -> Unit,
    viewModel: CitiesViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    CitiesScreen(state = state, onCityClick = onCityClick, onRetry = viewModel::load)
}

/** Čisti ekran: prima stanje i javlja događaje. Lako za @Preview. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CitiesScreen(
    state: CitiesUiState,
    onCityClick: (Int) -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = { CenterAlignedTopAppBar(title = { Text("Putopis") }) },
    ) { innerPadding ->
        when (state) {
            CitiesUiState.Loading -> Box(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator()
            }

            is CitiesUiState.Error -> Column(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(text = state.message)
                Button(onClick = onRetry) { Text("Pokušaj ponovno") }
            }

            is CitiesUiState.Success -> CitiesContent(
                cities = state.cities,
                onCityClick = onCityClick,
                modifier = Modifier.padding(innerPadding),
            )
        }
    }
}

@Composable
private fun CitiesContent(
    cities: List<City>,
    onCityClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    // rememberSaveable: tekst pretrage preživi rotaciju (običan remember ne bi)
    var query by rememberSaveable { mutableStateOf("") }
    val filtered = remember(cities, query) {
        cities.filter { it.name.contains(query, ignoreCase = true) }
    }

    Column(modifier = modifier) {
        SearchField(query = query, onQueryChange = { query = it })
        if (filtered.isEmpty()) {
            Text(text = "Nema rezultata", modifier = Modifier.padding(16.dp))
        } else {
            CityList(cities = filtered, onCityClick = { onCityClick(it.id) })
        }
    }
}

@Composable
fun SearchField(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        label = { Text("Traži grad") },
        singleLine = true,
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
    )
}

@Preview(showBackground = true)
@Composable
private fun CitiesScreenSuccessPreview() {
    PutopisTheme {
        CitiesScreen(
            state = CitiesUiState.Success(
                listOf(
                    City(1, "Zadar", "Zadarska", 21.0, 55, 8.0, WeatherType.CLEAR, emptyList()),
                    City(2, "Split", "Splitsko-dalmatinska", 24.0, 60, 12.0, WeatherType.CLOUDY, emptyList()),
                ),
            ),
            onCityClick = {},
            onRetry = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CitiesScreenErrorPreview() {
    PutopisTheme {
        CitiesScreen(state = CitiesUiState.Error("Nema mreže"), onCityClick = {}, onRetry = {})
    }
}