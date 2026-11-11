package org.unizd.rma.putopis.presentation.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import org.unizd.rma.putopis.domain.model.City
import org.unizd.rma.putopis.data.repository.FakeCityRepository
import org.unizd.rma.putopis.ui.theme.PutopisTheme

@Composable
fun CityDetailRoute(id: Int, onBack: () -> Unit) {
    // key: svaki grad dobiva vlastiti ViewModel (inače bi svi dijelili prvi stvoreni)
    val viewModel: CityDetailViewModel = viewModel(key = "city-$id") {
        CityDetailViewModel(id, FakeCityRepository())
    }
    val state by viewModel.state.collectAsStateWithLifecycle()
    CityDetailScreen(state = state, onBack = onBack)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CityDetailScreen(
    state: CityDetailUiState,
    onBack: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(state.city?.name ?: "") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Natrag")
                    }
                },
            )
        },
    ) { innerPadding ->
        val city = state.city
        when {
            state.loading -> Box(Modifier.fillMaxSize().padding(innerPadding), Alignment.Center) {
                CircularProgressIndicator()
            }
            city == null -> Box(Modifier.fillMaxSize().padding(innerPadding), Alignment.Center) {
                Text("Grad nije pronađen")
            }
            else -> Column(
                modifier = Modifier.padding(innerPadding).padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(text = city.name, style = MaterialTheme.typography.headlineMedium)
                Text(text = "Regija: ${city.region}", style = MaterialTheme.typography.bodyLarge)
                Text(text = "Temperatura: ${city.temp} °C", style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CityDetailScreenPreview() {
    PutopisTheme {
        CityDetailScreen(
            state = CityDetailUiState(City(1, "Zadar", "Zadarska", 21), loading = false),
            onBack = {},
        )
    }
}