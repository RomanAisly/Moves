package com.moves.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moves.ui.screens.SetLangScreen
import com.moves.ui.theme.yellow

@Composable
fun SettingMenuItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    setTitle: String
) {
    OutlinedButton(
        onClick = onClick, modifier = modifier
            .fillMaxWidth()
            .padding(4.dp),
        border = BorderStroke(width = 2.dp, color = yellow)
    ) {
        Text(
            text = setTitle,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 16.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    SetLangScreen()
}