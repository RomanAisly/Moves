package com.moves.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.graphics.Shape

@Composable
fun BaseScreen(
    modifier: Modifier = Modifier,
    useStatusBarsPadding: Boolean = true,
    useNavigationBarsPadding: Boolean = true,
    vertical: Arrangement.Vertical = Arrangement.Top,
    horizontal: Alignment.Horizontal = Alignment.Start,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .then(if (useStatusBarsPadding) Modifier.statusBarsPadding() else Modifier)
            .then(if (useNavigationBarsPadding) Modifier.navigationBarsPadding() else Modifier),
        verticalArrangement = vertical,
        horizontalAlignment = horizontal
    ) {
        content()
    }
}

@Composable
fun BaseCard(
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.large,
    border: BorderStroke? = null,
    content: @Composable (ColumnScope.() -> Unit)
) {
    Card(
        modifier = modifier,
        shape = shape,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onBackground),
        border = border,
        content = content
    )
}

