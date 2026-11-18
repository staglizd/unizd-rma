package org.unizd.rma.putopis.presentation.cities

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt
import org.unizd.rma.putopis.domain.model.City
import org.unizd.rma.putopis.domain.model.WeatherType
import org.unizd.rma.putopis.presentation.components.WeatherIcon
import org.unizd.rma.putopis.ui.theme.PutopisTheme

@Composable
fun CityRow(
    city: City,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column {
                Text(text = city.name, style = MaterialTheme.typography.titleMedium)
                Text(text = city.region, style = MaterialTheme.typography.bodySmall)
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                WeatherIcon(city.weather)
                Text(text = "${city.temp.roundToInt()} °C", style = MaterialTheme.typography.headlineSmall)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CityRowPreview() {
    PutopisTheme {
        CityRow(city = City(1, "Zadar", "Zadarska", 21.0, 55, 8.0, WeatherType.CLEAR, emptyList()), onClick = {})
    }
}