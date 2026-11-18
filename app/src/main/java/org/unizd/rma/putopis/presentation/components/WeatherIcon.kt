package org.unizd.rma.putopis.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material.icons.filled.Umbrella
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.unizd.rma.putopis.domain.model.WeatherType

@Composable
fun WeatherIcon(type: WeatherType, modifier: Modifier = Modifier) {
    val (icon, description) = when (type) {
        WeatherType.CLEAR -> Icons.Default.WbSunny to "Vedro"
        WeatherType.CLOUDY -> Icons.Default.Cloud to "Oblačno"
        WeatherType.RAIN -> Icons.Default.Umbrella to "Kiša"
        WeatherType.SNOW -> Icons.Default.AcUnit to "Snijeg"
        WeatherType.STORM -> Icons.Default.Bolt to "Oluja"
        WeatherType.UNKNOWN -> Icons.Default.QuestionMark to "Nepoznato"
    }
    Icon(icon, contentDescription = description, modifier = modifier)
}
