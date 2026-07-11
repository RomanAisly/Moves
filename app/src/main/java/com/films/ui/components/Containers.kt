package com.films.ui.components

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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.films.ui.theme.AppTheme
import com.films.ui.theme.transparent

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
            .background(AppTheme.colors.screenBack)
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
    containerColor: Color = AppTheme.colors.cardBack,
    elevation: Dp = 5.dp,
    shadowColor: Color = AppTheme.colors.text,
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

