package com.films.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.films.R
import com.films.core.utils.AppError
import com.films.ui.theme.white
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun CustomSnaKBar(
    snackBarFlow: Flow<AppError>,
    modifier: Modifier = Modifier
) {
    var currentType by remember { mutableStateOf<AppError?>(null) }

    LaunchedEffect(Unit) {
        snackBarFlow.collectLatest { type ->
            currentType = type
            delay(5000)
            currentType = null
        }
    }

    AnimatedVisibility(
        visible = currentType != null,
        enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
        exit = slideOutVertically(targetOffsetY = { it }) + fadeOut(),
        modifier = modifier
    ) {
        currentType?.let { type ->
            Snackbar(
                modifier = modifier.padding(horizontal = 18.dp),
                shape = MaterialTheme.shapes.large,
                containerColor = white,
                dismissAction = {
                    BaseIconButton(
                        modifier = Modifier.padding(end = 8.dp),
                        iconId = R.drawable.close,
                        onClick = { currentType = null }
                    )
                }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BaseIcon(iconId = type.iconRes)
                    Spacer(modifier = Modifier.width(12.dp))
                    BaseText(
                        text = stringResource(type.messageRes),
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}
