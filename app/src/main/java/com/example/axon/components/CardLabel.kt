package com.example.axon.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.example.axon.ui.theme.overusedGroteskFontFamily

@Composable
fun CardLabel(
    label: String,
    backgroundColor: Color,
    textColor: Color,
) {
    Surface(
        color = backgroundColor,
        shape = RoundedCornerShape(100)
    ){
        Text(
            text = label.uppercase(),
            fontFamily = overusedGroteskFontFamily,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = textColor,
            letterSpacing = 0.04.em,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
        )
    }
}