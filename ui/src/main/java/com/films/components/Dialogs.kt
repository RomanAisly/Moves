package com.films.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
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
import com.films.theme.BaseTheme
import com.films.theme.unspecified
import com.films.ui.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlin.time.Duration.Companion.seconds

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
            delay(5.seconds)
            isVisible = false
        }
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically(
            initialOffsetY = { it + 100 },
            animationSpec = tween(durationMillis = 400)
        ),
        exit = slideOutVertically(
            targetOffsetY = { it + 100 },
            animationSpec = tween(durationMillis = 400)
        ),
        modifier = modifier
    ) {
        displayItem?.let { item ->
            BaseCard(
                modifier = Modifier
                    .navigationBarsPadding()
                    .padding(bottom = 10.dp)
                    .padding(horizontal = 18.dp)
                    .fillMaxWidth(),
                elevation = 3.dp,
                shape = MaterialTheme.shapes.medium
            ) {
                Row(
                    modifier = Modifier.padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BaseIcon(iconId = iconRes(item), iconTint = iconTint(item))
                    Spacer(modifier = Modifier.width(12.dp))
                    BaseText(
                        text = stringResource(messageRes(item)),
                        modifier = Modifier.weight(1f)
                    )
                    BaseIconButton(
                        modifier = Modifier.padding(end = 8.dp),
                        iconId = R.drawable.close,
                        iconTint = BaseTheme.colors.text,
                        onClick = { isVisible = false }
                    )
                }
            }
        }
    }
}