package com.example.axon.components

import android.graphics.drawable.shapes.Shape
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp

@Composable
fun CategoryIcon(
    icon: Int,
    selected: Boolean,
    modifier: Modifier,
) {


    val iconColors = remember(icon){
        listOf(
            Color(0xFFFFE1F7) to Color(0xFFEC66C8),
            Color(0xFFDDDBFC) to Color(0xFF6056EE),
            Color(0xFFE8FCDB) to Color(0xFF80D648),
            Color(0xFFFCECDB) to Color(0xFFDF9243),
            Color(0xFFF5E4FF) to Color(0xFF8E25CF),
            Color(0xFFDBE7FC) to Color(0xFF2D69D5),
        ).random()
    }

    Box(
        modifier = Modifier.border(
            width = 1.dp,
            color = if (selected) iconColors.second else Color.Transparent,
            shape = CircleShape
        )
    ) {
        Surface(
            shape = CircleShape,
            color = iconColors.first,
            modifier = Modifier.padding(if (selected) 6.dp else 0.dp)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = icon),
                contentDescription = null,
                modifier = modifier.padding(10.dp).size(38.dp),
                tint = iconColors.second
            )
        }
    }
}
