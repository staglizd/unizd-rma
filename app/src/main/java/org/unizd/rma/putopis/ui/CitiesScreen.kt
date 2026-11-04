package org.unizd.rma.putopis.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.unizd.rma.putopis.ui.theme.PutopisTheme

/** Ekran s pretragom: drži stanje (query) i prosljeđuje ga "glupim" komponentama. */
@Composable
fun CitiesScreen(modifier: Modifier = Modifier) {
    var query by remember { mutableStateOf("") }
    val filtered = remember(query) {
        sampleCities.filter { it.name.contains(query, ignoreCase = true) }
    }

    Column(modifier = modifier) {
        SearchField(query = query, onQueryChange = { query = it })
        if (filtered.isEmpty()) {
            Text(text = "Nema rezultata", modifier = Modifier.padding(16.dp))
        } else {
            CityList(cities = filtered, onCityClick = { /* V3: navigacija na detalje */ })
        }
    }
}

/** Bez vlastitog stanja (stateless): vrijednost dolazi odozgo, događaj ide prema gore. */
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
private fun CitiesScreenPreview() {
    PutopisTheme { CitiesScreen() }
}