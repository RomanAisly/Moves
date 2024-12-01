package com.moves.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CategoryButton(modifier: Modifier = Modifier, changeCategory: () -> Unit, categoryName: String) {
    Button(
        onClick = changeCategory,
        modifier = modifier.height(36.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Yellow)
    ) {
        Text(
            text = categoryName,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}