package com.films.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import com.films.screens.details.DetailsScreen
import com.films.screens.details.DetailsViewModel
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf


@OptIn(ExperimentalSerializationApi::class)
@Composable
fun RootNavGraph() {
    val config = remember {
        SavedStateConfiguration {
            serializersModule = SerializersModule {
                polymorphic(NavKey::class) {
                    subclassesOfSealed<Routes>()
                }
            }
        }
    }
    val rootBackStack = rememberNavBackStack(config, Routes.BottomNavGraph)

    NavDisplay(
        backStack = rootBackStack,
        modifier = Modifier.fillMaxSize(),
        entryProvider = entryProvider {

            entry<Routes.BottomNavGraph> {
                BottomNavGraph(
                    onNavigateToDetails = { filmId ->
                        rootBackStack.add(Routes.Details(filmId))
                    }
                )
            }

            entry<Routes.Details> { route ->
                val viewModel: DetailsViewModel = koinViewModel(
                    key = route.id.toString(),
                    parameters = { parametersOf(route.id) })

                DetailsScreen(
                    viewModel = viewModel
                )
            }
        }
    )
}