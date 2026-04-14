package com.example.axon.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.axon.AxonViewModel
import com.example.axon.components.CategoryCard
import com.example.axon.components.ExpandedInputField
import com.example.axon.components.InputField
import com.example.axon.model.CardInputState
import com.example.axon.model.Category
import com.example.axon.model.QuestionAndAnswer
import com.example.axon.ui.theme.bluePrimary
import com.example.axon.ui.theme.grey200
import com.example.axon.ui.theme.ttHovesFontFamily
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onCategoryClick: (String) -> Unit,
    listOfItems: List<Category>,
    viewModel: AxonViewModel
) {
    var bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    var showBottomSheetState by remember {
        mutableStateOf(false)
    }
    var scopeState = rememberCoroutineScope()

    var questionInputState by remember{
        mutableStateOf("")
    }

    var answerInputState by remember{
        mutableStateOf("")
    }

    var categoryNameInputState by remember {
        mutableStateOf("")
    }

    var topicInputState by remember {
        mutableStateOf("")
    }

    Scaffold(
        topBar = {
                 TopAppBar(
                     title = {
                         Text(
                             text = "What would you like to learn today?",
                             fontSize = 18.sp,
                             fontWeight = FontWeight.Medium,
                             fontFamily = ttHovesFontFamily
                         )
                     },
                     modifier = Modifier
                         .statusBarsPadding()
                         .padding(horizontal = 8.dp)
                 )
        },
        floatingActionButton = {
            LargeFloatingActionButton(
                onClick = {
                    showBottomSheetState = !showBottomSheetState
                },
                containerColor = bluePrimary,
                contentColor = Color.White,
            ) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = "add",
                    Modifier.size(36.dp)
                )
            }
        }
    ) { paddingValues ->

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ){
            items(listOfItems){ category ->
                CategoryCard(
                    category,
                    onClick = {
                        onCategoryClick(category.categoryName)
                    }
                )
            }

        }

        if (showBottomSheetState) {
            BottomSheetModal(
                modifier = Modifier.statusBarsPadding().padding(bottom = 40.dp),
                onShowBottomSheetChanged = { showBottomSheetState = !showBottomSheetState},
                bottomSheetState = bottomSheetState,
                scope = scopeState,
                inputState = CardInputState(
                    categoryName = categoryNameInputState,
                    question = questionInputState,
                    answer = answerInputState,
                    topic = topicInputState,
                    onCategoryNameChanged = { newValue ->
                        categoryNameInputState = newValue
                    },
                    onQuestionChanged = { newValue ->
                        questionInputState = newValue
                    },
                    onAnswerChanged = { newValue ->
                        answerInputState = newValue
                    },
                    onTopicChanged = { newValue ->
                        topicInputState = newValue
                    }
                ),
                onAddNewInfo = { categoryName, topicName, questionAndAnswer ->
                    viewModel.addNewContent(categoryName, topicName, QuestionAndAnswer(questionAndAnswer.question, questionAndAnswer.answer))

                    categoryNameInputState = ""
                    topicInputState = ""
                    questionInputState = ""
                    answerInputState = ""
                }
            )
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun BottomSheetModal(
    modifier: Modifier,
    onShowBottomSheetChanged: () -> Unit,
    bottomSheetState: SheetState,
    scope: CoroutineScope,
    inputState: CardInputState,
    onAddNewInfo: (String, String, QuestionAndAnswer) -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onShowBottomSheetChanged,
        dragHandle = null,
        containerColor = grey200,
        scrimColor = Color.Black.copy(0.6f),
        sheetState = bottomSheetState,
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .padding(24.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    IconButton(
                        onClick = {
                            scope.launch {
                                bottomSheetState.hide()
                            }.invokeOnCompletion {
                                if (!bottomSheetState.isVisible) onShowBottomSheetChanged()
                            }
                        },
                        colors = IconButtonDefaults.iconButtonColors(Color.White),
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Close,
                            contentDescription = "hide the dialog",
                            tint = Color.Black
                        )
                    }
                }
                Text(
                    text = "New Content",
                    fontFamily = ttHovesFontFamily,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                )

                Box(
                    modifier = Modifier.weight(1f)
                ) {}
            }

            Spacer(modifier = Modifier.height(24.dp))

            InputField(
                userInput = inputState.categoryName,
                onUserInputChanged = { newValue ->
                    inputState.onCategoryNameChanged(newValue)
                },
                label = "Category"
            )

            InputField(
                userInput = inputState.topic,
                onUserInputChanged = { newValue ->
                    inputState.onTopicChanged(newValue)
                },
                label = "Topic"
            )

            InputField(
                userInput = inputState.question,
                onUserInputChanged = { newValue ->
                   inputState.onQuestionChanged(newValue)
                },
                label = "Question"
            )
            Spacer(modifier = Modifier.height(24.dp))
            ExpandedInputField(
                userInput = inputState.answer,
                onUserInputChanged = { newValue ->
                  inputState.onAnswerChanged(newValue)
                },
                label = "Answer to question"
            )
            Spacer(modifier = Modifier.height(36.dp))

            Button(
                onClick = {
                    onAddNewInfo(
                        inputState.categoryName,
                        inputState.topic,
                        QuestionAndAnswer(inputState.question, inputState.answer)
                    )
                    scope.launch {
                        bottomSheetState.hide()
                    }.invokeOnCompletion {
                        if(!bottomSheetState.isVisible) onShowBottomSheetChanged()
                    }
                },
                colors = ButtonDefaults.buttonColors(bluePrimary),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                Text(
                    text = "Add",
                    fontFamily = ttHovesFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}