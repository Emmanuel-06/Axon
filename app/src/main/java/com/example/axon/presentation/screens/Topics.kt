package com.example.axon.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.axon.AxonViewModel
import com.example.axon.components.TopicsCard
import com.example.axon.ui.theme.grey200
import com.example.axon.ui.theme.ttHovesFontFamily


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Topics(
    axonViewModel: AxonViewModel,
    onNavigateBack: () -> Unit,
    onTopicClick: (String, Int) -> Unit,
    categoryId: String,
) {

    val category = axonViewModel.categories.value.find {category ->
        category.categoryName == categoryId
    } ?: return

    val topics by axonViewModel.topics.collectAsState()

    LaunchedEffect(key1 = categoryId){
        axonViewModel.selectCategory(categoryId)

    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = categoryId,
                        fontFamily = ttHovesFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp,
                        color = Color.Black,
                        modifier = Modifier
                            .statusBarsPadding()
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(grey200),

                navigationIcon = {
                    IconButton(onClick = {
                        onNavigateBack() },
                        colors = IconButtonDefaults.iconButtonColors(Color.White),
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
        },
        containerColor = grey200

    ) { paddingValues ->

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(21.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(paddingValues)
                .padding(18.dp)
        ) {
            items(items = topics){ topic->
                TopicsCard(
                    topic = topic.topicName
                ) {
                    onTopicClick(category.categoryName, topic.topicId)

                }
            }
        }
    }

}
