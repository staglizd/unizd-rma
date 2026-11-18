package org.unizd.rma.putopis.presentation.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import kotlin.math.roundToInt
import org.unizd.rma.putopis.PutopisApp
import org.unizd.rma.putopis.domain.model.City
import org.unizd.rma.putopis.domain.model.DayForecast
import org.unizd.rma.putopis.domain.model.WeatherType
import org.unizd.rma.putopis.presentation.components.WeatherIcon
import org.unizd.rma.putopis.ui.theme.PutopisTheme

@Composable
fun CityDetailRoute(id: Int, onBack: () -> Unit) {
    val container = (LocalContext.current.applicationContext as PutopisApp).container
    // key: svaki grad dobiva vlastiti ViewModel (inače bi svi dijelili prvi stvoreni)
    val viewModel: CityDetailViewModel = viewModel(key = "city-$id") {
        CityDetailViewModel(id, container.cityRepository)
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
            else -> CityDetailContent(city = city, modifier = Modifier.padding(innerPadding))
        }
    }
}

@Composable
private fun CityDetailContent(city: City, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
                WeatherIcon(city.weather)
                Text("${city.temp.roundToInt()} °C", style = MaterialTheme.typography.headlineMedium)
            }
            Text("Regija: ${city.region}", style = MaterialTheme.typography.bodyLarge)
            Text("Vlaga: ${city.humidity} %", style = MaterialTheme.typography.bodyLarge)
            Text("Vjetar: ${city.windSpeed} km/h", style = MaterialTheme.typography.bodyLarge)
            Text("Prognoza", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(top = 16.dp))
        }
        items(city.dailyForecast, key = { it.date.toString() }) { day ->
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(day.date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)))
                Text("${day.min.roundToInt()}° / ${day.max.roundToInt()}°")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CityDetailScreenPreview() {
    PutopisTheme {
        CityDetailScreen(
            state = CityDetailUiState(
                city = City(
                    1, "Zadar", "Zadarska", 21.0, 55, 8.0, WeatherType.CLEAR,
                    listOf(DayForecast(LocalDate.of(2026, 11, 12), 12.0, 19.0)),
                ),
                loading = false,
            ),
            onBack = {},
        )
    }
}
