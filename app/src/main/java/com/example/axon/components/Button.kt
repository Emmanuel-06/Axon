package com.example.axon.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.axon.ui.theme.ttHovesFontFamily


@Composable
fun ButtonComp(
    color: ButtonColors,
    buttonText: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = color,
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
    ) {
        Text(
            text = buttonText,
            fontFamily = ttHovesFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        )
    }
}
