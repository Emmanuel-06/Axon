package com.example.axon.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.example.axon.AxonViewModel
import com.example.axon.ui.theme.bluePrimary
import com.example.axon.ui.theme.grey200
import com.example.axon.ui.theme.ttHovesFontFamily

//TODO
//Slide animation between cards (combine with your flip)
//Previous button
//Progress indicator (e.g. 3 / 20)
//Shuffle mode
//Category filtering (you already started this 👀)

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun QuestionandAnswer(
//    axonViewModel: AxonViewModel,
//    onNavigateBack: () -> Unit,
//) {
//    var rotated by remember {
//        mutableStateOf(false)
//    }
//    val duration = 500
//
//    val rotation by animateFloatAsState(
//        targetValue = if (rotated) 180f else 0f,
//        animationSpec = tween(duration),
//        label = ""
//    )
//
//    val currentItemDisplayedQuestion = axonViewModel.content[axonViewModel.currentIndex].content.question
//    val currentItemDisplayedAnswer = axonViewModel.content[axonViewModel.currentIndex].content.answer
//
//    Scaffold(
//        topBar = {
//            CenterAlignedTopAppBar(
//                title = {
//                    Text(
//                        text = "Software Engineering",
//                        fontFamily = ttHovesFontFamily,
//                        fontWeight = FontWeight.SemiBold,
//                        fontSize = 20.sp,
//                        color = Color.Black,
//                        modifier = Modifier
//                            .statusBarsPadding()
//                    )
//                },
//                colors = TopAppBarDefaults.topAppBarColors(grey200),
//
//                navigationIcon = {
//                    IconButton(onClick = {
//                        onNavigateBack() },
//                        colors = IconButtonDefaults.iconButtonColors(Color.White),
//                        modifier = Modifier.size(48.dp)
//                    ) {
//                        Icon(imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
//                            contentDescription = null
//                        )
//                    }
//                },
//                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
//            )
//        },
//        containerColor = grey200,
//        modifier = Modifier
//            .fillMaxSize()
//
//    ) { paddingValues ->
//
//        Column(
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Top,
//            modifier = Modifier
//                .padding(paddingValues)
//                .padding(horizontal = 21.dp, vertical = 82.dp)
//                .fillMaxSize()
//        ) {
//            CardComponent(
//                onRotatedChanged = {
//                    rotated = !rotated
//                },
//                rotation = rotation,
//                question = currentItemDisplayedQuestion,
//                answer = currentItemDisplayedAnswer,
//                modifier = Modifier
//                    .fillMaxHeight()
//                    .weight(1f)
//            )
//
//            Spacer(modifier = Modifier.height(60.dp))
//
//            Row(
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.SpaceBetween,
//                modifier = Modifier.fillMaxWidth()
//            ) {
//
//                Button(
//                    onClick = {
//                        axonViewModel.prevQuestion()
//                        rotated = false
//                    },
//                    shape = RoundedCornerShape(30.dp),
//                    colors = ButtonDefaults.buttonColors(bluePrimary),
//                    modifier = Modifier
//                        .height(46.dp)
//                        .width(120.dp)
//                ) {
//                    Text(
//                        text = "Previous",
//                        fontFamily = ttHovesFontFamily,
//                        fontWeight = FontWeight.Normal,
//                        fontSize = 16.sp
//                    )
//                }
//
//                Button(
//                    onClick = {
//                        axonViewModel.nextQuestion()
//                        rotated = false
//                    },
//                    shape = RoundedCornerShape(30.dp),
//                    colors = ButtonDefaults.buttonColors(bluePrimary),
//                    modifier = Modifier
//                        .height(46.dp)
//                        .width(120.dp)
//                ) {
//                    Text(
//                        text = "Next",
//                        fontFamily = ttHovesFontFamily,
//                        fontWeight = FontWeight.Normal,
//                        fontSize = 16.sp
//                    )
//                }
//            }
//
//        }
//    }
//}
//
//
//@Composable
//fun CategoryItem(
////    selectedTabIndex: Int,
////    categoryText: String
//) {
//    var selectedTabIndex by remember {
//        mutableStateOf(0)
//    }
//
//    val categories = listOf(
//        "Kotlin",
//        "Compose",
//        "Design",
//        "Data Structures",
//        "Git & Github"
//    )
//
//    val scrollState = rememberScrollState()
//
//    Row(
//        horizontalArrangement = Arrangement.spacedBy(0.dp),
//        verticalAlignment = Alignment.CenterVertically,
//        modifier = Modifier
//            .fillMaxWidth()
//            .horizontalScroll(scrollState)
//            .wrapContentWidth()
//    ) {
//        categories.forEachIndexed { index, label ->
//            val isSelected = selectedTabIndex == index
//            TextButton(
//                onClick = {
//                    selectedTabIndex = index
//                },
//                shape = RoundedCornerShape(10.dp),
//                contentPadding = PaddingValues(0.dp),
//                modifier = Modifier
//            ) {
//                Text(
//                    text = label,
//                    fontSize = 14.sp,
//                    fontFamily = ttHovesFontFamily,
//                    fontWeight = FontWeight.Medium,
//                    color = if (isSelected) Color.Black else Color.Gray,
//                    modifier = Modifier
//                        .background(
//                            color = if (isSelected) Color.White else Color.Transparent,
//                            shape = RoundedCornerShape(8.dp)
//                        )
//                        .padding(horizontal = 16.dp, vertical = 10.dp)
//                )
//            }
//        }
//    }
//
//}
//
//
//@Composable
//fun CardComponent(
//    onRotatedChanged: () -> Unit,
//    rotation: Float,
//    question: String,
//    answer: String,
//    modifier: Modifier = Modifier,
//) {
//    Card(
//        shape = RoundedCornerShape(18.dp),
//        onClick = {
//            onRotatedChanged()
//        },
//        modifier = modifier
//            .fillMaxWidth()
//            .graphicsLayer {
//                rotationY = rotation
//                cameraDistance = 12 * density
//            }
//    ) {
//        if (rotation <= 90) {
//            Question(questions = question)
//        } else {
//            Answers(answers = answer)
//        }
//
//    }
//
//}
//
//@Composable
//fun Question(
//    questions: String,
//) {
//    Column(
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally,
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color(0xFF092961))
//            .padding(16.dp)
//    ) {
//        Text(
//            text = questions,
//            fontFamily = ttHovesFontFamily,
//            fontSize = 24.sp,
//            fontWeight = FontWeight.Medium,
//            color = Color.White,
//            textAlign = TextAlign.Center
//        )
//    }
//}
//
//@Composable
//fun Answers(
//    answers: String,
//) {
//    Column(
//        verticalArrangement = Arrangement.Top,
//        horizontalAlignment = Alignment.CenterHorizontally,
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color(0xFFFFFFFF))
//            .padding(32.dp)
//            .graphicsLayer {
//                rotationY = 180f
//            }
//    ) {
//        Text(
//            text = answers,
//            fontFamily = ttHovesFontFamily,
//            fontSize = 18.sp,
//            lineHeight = 1.5.em,
//            fontWeight = FontWeight.Normal,
//            color = Color.Black,
////            textAlign = TextAlign.Center
//        )
//    }
//}