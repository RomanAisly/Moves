package com.moves.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.moves.R


@Composable
fun ArrowBackButton(
    modifier: Modifier = Modifier,
    iconTint: Color = Color.Unspecified,
    onClick: () -> Unit
) {
    IconButton(
        modifier = modifier
            .padding(start = 16.dp, top = 16.dp)
            .shadow(elevation = 2.dp, shape = CircleShape)
            .size(48.dp),
        shape = CircleShape,
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        onClick = onClick
    ) {
        Icon(
            painter = painterResource(R.drawable.arrow_back),
            contentDescription = null,
            tint = iconTint
        )
    }
}

@Composable
fun TabButton(
    modifier: Modifier = Modifier,
    textPadding: Dp = 0.dp,
    tabName: String,
    tabColor: Color = MaterialTheme.colorScheme.primary,
    textColor: Color = MaterialTheme.colorScheme.onBackground,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(containerColor = tabColor),
        onClick = onClick
    ) {
        BaseText(
            text = tabName,
            textColor = textColor,
            modifier = Modifier.padding(textPadding)
        )
    }
}
