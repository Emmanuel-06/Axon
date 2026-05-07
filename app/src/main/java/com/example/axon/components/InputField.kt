package com.example.axon.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.axon.ui.theme.blueSecondary
import com.example.axon.ui.theme.ttHovesFontFamily


@Composable
fun InputField(
    userInput: String,
    onUserInputChanged: (String) -> Unit,
    trailingIcon: @Composable ()-> Unit = {},
    label: String,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = userInput,
        onValueChange = { newValue ->
            onUserInputChanged(newValue)
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedBorderColor = blueSecondary.copy(0f),
            unfocusedBorderColor = blueSecondary.copy(0f)
        ),
        shape = RoundedCornerShape(12.dp),
        textStyle = TextStyle(
            fontFamily = ttHovesFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        ),
        label = {
            Text(
                text = label,
                fontSize = 16.sp,
                fontFamily = ttHovesFontFamily,
                fontWeight = FontWeight.Normal
            )
        },
        trailingIcon = { trailingIcon() },
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    )
}

@Composable
fun ExpandedInputField(
    userInput: String,
    onUserInputChanged: (String) -> Unit,
    label: String,
) {
    OutlinedTextField(
        value = userInput,
        onValueChange = { newValue ->
            onUserInputChanged(newValue)
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedBorderColor = blueSecondary.copy(0f),
            unfocusedBorderColor = blueSecondary.copy(0f)
        ),
        textStyle = TextStyle(
            fontFamily = ttHovesFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        ),
        shape = RoundedCornerShape(12.dp),
        label = {
            Text(
                text = label,
                fontSize = 16.sp,
                fontFamily = ttHovesFontFamily,
                fontWeight = FontWeight.Normal
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(200.dp, 500.dp)
    )
}