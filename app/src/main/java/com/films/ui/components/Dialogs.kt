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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.films.R
import com.films.ui.theme.AppTheme
import com.films.ui.theme.unspecified
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun <T> SnackBarFlow(
    snackBarFlow: Flow<T>,
    iconRes: (T) -> Int,
    messageRes: (T) -> Int,
    modifier: Modifier = Modifier,
    iconTint: (T) -> Color = { unspecified }
) {
    var isVisible by remember { mutableStateOf(false) }
    var displayItem by remember { mutableStateOf<T?>(null) }

    LaunchedEffect(snackBarFlow) {
        snackBarFlow.collectLatest { item ->
            displayItem = item
            isVisible = true
            delay(5000)
            isVisible = false
        }
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
        exit = slideOutVertically(targetOffsetY = { it }) + fadeOut(),
        modifier = modifier
    ) {
        displayItem?.let { item ->
            Snackbar(
                modifier = Modifier.padding(horizontal = 18.dp),
                shape = MaterialTheme.shapes.medium,
                containerColor = AppTheme.colors.cardBack,
                dismissAction = {
                    BaseIconButton(
                        modifier = Modifier.padding(end = 8.dp),
                        iconId = R.drawable.close,
                        iconTint = AppTheme.colors.text,
                        onClick = { isVisible = false }
                    )
                }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BaseIcon(iconId = iconRes(item), iconTint = iconTint(item))
                    Spacer(modifier = Modifier.width(12.dp))
                    BaseText(
                        text = stringResource(messageRes(item)),
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}