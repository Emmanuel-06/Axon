package com.example.axon.components

import android.app.AlertDialog
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.axon.ui.theme.overusedGroteskFontFamily

@Composable
fun Dialog(
    dialogTitle: String,
    dialogText: String,
    icon: Int,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,

    ) {
    AlertDialog(
        onDismissRequest = {
                           onDismissRequest()
        },
        confirmButton = {
            ButtonComp(
                color = ButtonDefaults.buttonColors(Color.Red),
                buttonText = "Yes",
                modifier = Modifier
                    .width(100.dp)
                    .height(46.dp)
            ) {
                onConfirmation()
            }
        },
        dismissButton = {
            ButtonComp(
                color = ButtonDefaults.buttonColors(Color.Gray.copy(0.2f)),
                buttonText = "No",
                textColor = Color.Black,
                modifier = Modifier
                    .width(100.dp)
                    .height(46.dp)
            ) {
                onDismissRequest()
            }

        },
        icon = {
            Icon(
                imageVector = ImageVector.vectorResource(icon),
                contentDescription = null,
                tint = Color.Red,
                modifier = Modifier.size(46.dp)
            )
        },
        shape = RoundedCornerShape(18.dp),
        title = {
            Text(
                text = dialogTitle,
                fontFamily = overusedGroteskFontFamily,
                fontWeight = FontWeight.SemiBold
            )
        },
        text = {
            Text(
                text = dialogText,
                fontFamily = overusedGroteskFontFamily,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
        },
        containerColor = Color.White
    )
}