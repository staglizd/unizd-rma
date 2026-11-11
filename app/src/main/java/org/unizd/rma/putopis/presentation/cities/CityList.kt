package org.unizd.rma.putopis.presentation.cities

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.unizd.rma.putopis.domain.model.City
import org.unizd.rma.putopis.ui.theme.PutopisTheme

@Composable
fun CityList(
    cities: List<City>,
    onCityClick: (City) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier) {
        items(items = cities, key = { it.id }) { city ->
            CityRow(city = city, onClick = { onCityClick(city) })
        }
    }
}

