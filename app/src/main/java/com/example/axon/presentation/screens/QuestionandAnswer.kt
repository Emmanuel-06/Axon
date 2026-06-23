package com.example.axon.presentation.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Label
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import com.example.axon.components.CardLabel
import com.example.axon.ui.theme.bluePrimary
import com.example.axon.ui.theme.grey200
import com.example.axon.ui.theme.overusedGroteskFontFamily

//TODO
//Slide animation between cards (combine with your flip)
//Previous button
//Progress indicator (e.g. 3 / 20)
//Shuffle mode
//Category filtering (you already started this 👀)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionAndAnswer(
    axonViewModel: AxonViewModel,
    topicId: Int,
    topicName: String,
    onNavigateBack: () -> Unit,
) {


    val questions = axonViewModel.questionsAndAnswers.collectAsState().value

    val currentIndex = axonViewModel.currentIndex.collectAsState().value

    var rotated by remember {
        mutableStateOf(false)
    }

    val duration = 500

    val rotation by animateFloatAsState(
        targetValue = if (rotated) 180f else 0f,
        animationSpec = tween(duration),
        label = ""
    )

    LaunchedEffect(key1 = topicId){
        axonViewModel.selectTopic(topicId)
    }

//    val currentItemDisplayedQuestion = axonViewModel.content[axonViewModel.currentIndex].content.question
//    val currentItemDisplayedAnswer = axonViewModel.content[axonViewModel.currentIndex].content.answer

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = topicName,
                        fontFamily = overusedGroteskFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp,
                        color = Color.Black,
                        modifier = Modifier
                            .statusBarsPadding()
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(grey200),

                navigationIcon = {
                    IconButton(
                        onClick = {
                            onNavigateBack()
                        },
                        colors = IconButtonDefaults.iconButtonColors(Color.White),
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
        },
        containerColor = grey200,
        modifier = Modifier
            .fillMaxSize()

    ) { paddingValues ->

        if(questions.isEmpty()){
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator()
            }
        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(horizontal = 21.dp, vertical = 60.dp)

            ) {
                if (questions.isEmpty()) {
                    Text("No questions yet")
                } else {
                    CardComponent(
                        onRotatedChanged = {
                            rotated = !rotated
                        },
                        rotation = rotation,
                        question = questions[currentIndex].question,
                        answer = questions[currentIndex].answer,
                        modifier = Modifier.height(500.dp)
                    )

                }

                Spacer(modifier = Modifier.height(60.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Button(
                        onClick = {
                            axonViewModel.prevQuestion()
                            rotated = false
                        },
                        shape = RoundedCornerShape(30.dp),
                        colors = ButtonDefaults.buttonColors(bluePrimary),
                        modifier = Modifier
                            .height(46.dp)
                            .width(120.dp)
                    ) {
                        Text(
                            text = "Previous",
                            fontFamily = overusedGroteskFontFamily,
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp
                        )
                    }

                    Button(
                        onClick = {
                            axonViewModel.nextQuestion()
                            rotated = false
                        },
                        shape = RoundedCornerShape(30.dp),
                        colors = ButtonDefaults.buttonColors(bluePrimary),
                        modifier = Modifier
                            .height(46.dp)
                            .width(120.dp)
                    ) {
                        Text(
                            text = "Next",
                            fontFamily = overusedGroteskFontFamily,
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp
                        )
                    }
                }

            }

        }

    }
}


@Composable
fun CardComponent(
    onRotatedChanged: () -> Unit,
    rotation: Float,
    question: String,
    answer: String,
    modifier: Modifier = Modifier,
) {
    Card(
        shape = RoundedCornerShape(24.dp),
        onClick = {
            onRotatedChanged()
        },
//        elevation = CardDefaults.cardElevation(4.dp),
        modifier = modifier
            .fillMaxWidth()
            .graphicsLayer {
                rotationY = rotation
                cameraDistance = 12 * density
            }
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxHeight()
        ) {

            if (rotation <= 90) {
                Question(questions = question)
            } else {
                Answers(answers = answer)
            }
        }
    }

}

@Composable
fun Question(
    questions: String,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF092961))
            .padding(16.dp)
    ) {
        CardLabel(
            label = "QUESTION",
            backgroundColor = Color(0xFF0E3477),
            textColor = Color.White.copy(alpha = 0.6f)
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = questions,
            fontFamily = overusedGroteskFontFamily,
            fontSize = 32.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "tap to reveal answer",
            fontFamily = overusedGroteskFontFamily,
            fontSize = 16.sp,
            color = Color.White.copy(alpha = 0.6f)
        )
    }
}

@Composable
fun Answers(
    answers: String,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF))
            .padding(32.dp)
            .graphicsLayer {
                rotationY = 180f
            }
    ) {

        CardLabel(
            label = "ANSWER",
            backgroundColor = Color(0xFFF0F0F1),
            textColor = Color.Black
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = answers,
            fontFamily = overusedGroteskFontFamily,
            fontSize = 24.sp,
            lineHeight = 1.5.em,
            fontWeight = FontWeight.Medium,
            color = Color.Black,
            textAlign = TextAlign.Center
        )
    }
}