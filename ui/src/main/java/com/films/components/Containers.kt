package com.films.components

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.layout.PaneAdaptedValue
import androidx.compose.material3.adaptive.layout.ThreePaneScaffoldValue
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.films.screens.details.DetailsScreen
import com.films.screens.details.DetailsViewModel
import com.films.theme.BaseTheme
import com.films.theme.transparent
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun BaseScreen(
    modifier: Modifier = Modifier,
    useStatusBarsPadding: Boolean = true,
    useNavigationBarsPadding: Boolean = true,
    vertical: Arrangement.Vertical = Arrangement.Top,
    horizontal: Alignment.Horizontal = Alignment.Start,
    overlayContent: @Composable (BoxScope.() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BaseTheme.colors.screenBack)
            .then(if (useStatusBarsPadding) Modifier.statusBarsPadding() else Modifier)
            .then(if (useNavigationBarsPadding) Modifier.navigationBarsPadding() else Modifier)
    ) {
        Column(
            modifier = modifier
                .fillMaxSize(),
            verticalArrangement = vertical,
            horizontalAlignment = horizontal,
            content = content
        )
        if (overlayContent != null) {
            overlayContent()
        }
    }
}

@Composable
fun BaseCard(
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.large,
    containerColor: Color = BaseTheme.colors.cardBack,
    elevation: Dp = 5.dp,
    shadowColor: Color = BaseTheme.colors.text,
    border: BorderStroke? = null,
    content: @Composable (ColumnScope.() -> Unit)
) {
    Card(
        modifier = modifier.then(
            if (elevation > 0.dp && shadowColor != transparent) {
                Modifier.shadow(
                    elevation = elevation,
                    shape = shape,
                    ambientColor = shadowColor,
                    spotColor = shadowColor
                )
            } else Modifier
        ),
        shape = shape,
        colors = CardDefaults.cardColors(containerColor = containerColor),
        border = border,
        content = content
    )
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun AdaptiveFilmListDetailPane(listPaneContent: @Composable (onFilmClick: (Int) -> Unit) -> Unit) {

    var selectedFilmId by rememberSaveable { mutableStateOf<Int?>(null) }
    val navigator = rememberListDetailPaneScaffoldNavigator<Int>()
    val scope = rememberCoroutineScope()

    BackHandler(enabled = selectedFilmId != null) {
        selectedFilmId = null
        scope.launch { navigator.navigateBack() }
    }

    val customScaffoldValue = if (selectedFilmId == null) {
        ThreePaneScaffoldValue(
            primary = PaneAdaptedValue.Hidden,
            secondary = PaneAdaptedValue.Expanded,
            tertiary = PaneAdaptedValue.Hidden
        )
    } else {
        navigator.scaffoldValue
    }

    ListDetailPaneScaffold(
        modifier = Modifier.background(BaseTheme.colors.screenBack),
        directive = navigator.scaffoldDirective,
        value = customScaffoldValue,
        listPane = {
            AnimatedPane(
                enterTransition = slideInHorizontally(tween(500)) { -it } + fadeIn(tween(500)),
                exitTransition = slideOutHorizontally(tween(500)) { -it } + fadeOut(tween(500)),
                boundsAnimationSpec = spring(
                    dampingRatio = Spring.DampingRatioLowBouncy,
                    stiffness = Spring.StiffnessMediumLow
                )
            ) {
                listPaneContent { filmId ->
                    selectedFilmId = filmId
                    scope.launch {
                        navigator.navigateTo(ListDetailPaneScaffoldRole.Detail, filmId)
                    }
                }
            }
        },
        detailPane = {
            AnimatedPane(
                enterTransition = slideInHorizontally(tween(500)) { it } + fadeIn(tween(500)),
                exitTransition = slideOutHorizontally(tween(500)) { it } + fadeOut(tween(500)),
                boundsAnimationSpec = spring(
                    dampingRatio = Spring.DampingRatioLowBouncy,
                    stiffness = Spring.StiffnessMediumLow
                )
            ) {
                if (selectedFilmId != null) {
                    val detailsViewModel: DetailsViewModel = koinViewModel(
                        key = selectedFilmId.toString(),
                        parameters = { parametersOf(selectedFilmId) }
                    )
                    DetailsScreen(
                        viewModel = detailsViewModel,
                        onBack = {
                            selectedFilmId = null
                            scope.launch { navigator.navigateBack() }
                        }
                    )
                }

            }
        }
    )
}

