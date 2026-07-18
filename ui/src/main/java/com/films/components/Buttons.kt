package com.films.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.films.theme.BaseTheme

@Composable
fun TabButton(
    modifier: Modifier = Modifier,
    textPadding: Dp = 0.dp,
    tabName: String,
    tabColor: Color = BaseTheme.colors.buttPrimary,
    textColor: Color = BaseTheme.colors.text,
    border: BorderStroke? = null,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(containerColor = tabColor),
        border = border,
        onClick = onClick
    ) {
        BaseText(
            text = tabName,
            textColor = textColor,
            textStyle = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(textPadding)
        )
    }
}
