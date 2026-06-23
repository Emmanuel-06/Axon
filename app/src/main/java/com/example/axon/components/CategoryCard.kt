package com.example.axon.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.axon.model.Category
import com.example.axon.ui.theme.overusedGroteskFontFamily


@Composable
fun CategoryCard(
    category: Category,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(Color.White),
        modifier = modifier.height(160.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxSize().padding(horizontal = 18.dp, vertical = 10.dp)
        ) {
            CategoryIcon(
                icon = category.icon,
                selected = false,
                modifier = Modifier.size(56.dp).weight(1f)
            )

//            Spacer(modifier = Modifier.height(42.dp))

            Text(
                text = category.categoryName,
                fontFamily = overusedGroteskFontFamily,
                fontSize = 20.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
            )

//            Surface(
//                shape = RoundedCornerShape(60.dp),
//                color = Color.Transparent,
//                border = BorderStroke(1.dp, Color.Black.copy(0.1f)),
//                modifier = Modifier
//            ) {
//                Text(
//                    text = "${categories.noOfQuestions} Questions",
//                    fontFamily = overusedGroteskFontFamily,
//                    fontSize = 14.sp,
//                    fontWeight = FontWeight.Normal,
//                    color = Color.Black.copy(0.8f),
//                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
//                )
//
//            }
        }

    }


    
}