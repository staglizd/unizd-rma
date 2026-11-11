package org.unizd.rma.putopis.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import kotlinx.serialization.Serializable
import org.unizd.rma.putopis.presentation.cities.CitiesRoute
import org.unizd.rma.putopis.presentation.detail.CityDetailRoute

/** Odredišta (rute) aplikacije: Kotlin objekti koji opisuju ekran i njegove parametre. */
@Serializable
data object CitiesKey : NavKey

@Serializable
data class CityDetailKey(val id: Int) : NavKey

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    // back stack je obična lista; vrh liste = ekran koji se prikazuje
    val backStack = rememberNavBackStack(CitiesKey)

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator(),
        ),
        entryProvider = entryProvider {
            entry<CitiesKey> {
                CitiesRoute(onCityClick = { id -> backStack.add(CityDetailKey(id)) })
            }
            entry<CityDetailKey> { key ->
                CityDetailRoute(id = key.id, onBack = { backStack.removeLastOrNull() })
            }
        },
        modifier = modifier,
    )
}