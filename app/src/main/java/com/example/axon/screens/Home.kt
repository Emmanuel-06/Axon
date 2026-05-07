package com.example.axon.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateValueAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.axon.AxonViewModel
import com.example.axon.R
import com.example.axon.components.ButtonComp
import com.example.axon.components.CategoryCard
import com.example.axon.components.CategoryIcon
import com.example.axon.components.ExpandedInputField
import com.example.axon.components.InputField
import com.example.axon.model.CardInputState
import com.example.axon.model.Category
import com.example.axon.model.QuestionAndAnswer
import com.example.axon.ui.theme.bluePrimary
import com.example.axon.ui.theme.grey200
import com.example.axon.ui.theme.ttHovesFontFamily
import com.example.axon.util.BottomSheetModals
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onCategoryClick: (String) -> Unit,
    listOfItems: List<Category>,
    viewModel: AxonViewModel,
) {
    var bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    var showBottomSheetState by remember {
        mutableStateOf(false)
    }
    var scopeState = rememberCoroutineScope()

    var questionInputState by remember {
        mutableStateOf("")
    }

    var answerInputState by remember {
        mutableStateOf("")
    }

    var categoryNameInputState by remember {
        mutableStateOf("")
    }

    var topicInputState by remember {
        mutableStateOf("")
    }

    var categoryToAddState by remember {
        mutableStateOf("")
    }

    var currentSheet by remember {
        mutableStateOf(BottomSheetModals.ADD_CATEGORY)
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
                    currentSheet = BottomSheetModals.ADD_CATEGORY
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
        ) {
            items(listOfItems) { category ->
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
                currentSheet = currentSheet,
                onCurrentSheetChanged = { currentSheet = BottomSheetModals.CREATE_NEW_CATEGORY },
                modifier = Modifier
                    .padding(bottom = 40.dp),
                onShowBottomSheetChanged = { showBottomSheetState = !showBottomSheetState },
                bottomSheetState = bottomSheetState,
                onBack = { currentSheet = BottomSheetModals.ADD_CATEGORY },
                scope = scopeState,
                inputState = CardInputState(
                    categoryName = categoryNameInputState,
                    categoryToAdd = categoryToAddState,
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
                    },
                    onCategoryToAddChanged = { newValue ->
                        categoryToAddState = newValue

                    }
                ),
                onAddNewInfo = { categoryName, topicName, questionAndAnswer ->
                    viewModel.addNewContent(
                        categoryName,
                        topicName,
                        QuestionAndAnswer(questionAndAnswer.question, questionAndAnswer.answer)
                    )

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
    currentSheet: BottomSheetModals,
    onCurrentSheetChanged: () -> Unit,
    modifier: Modifier,
    onShowBottomSheetChanged: () -> Unit,
    bottomSheetState: SheetState,
    onBack: () -> Unit,
    scope: CoroutineScope,
    inputState: CardInputState,
    onAddNewInfo: (String, String, QuestionAndAnswer) -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = onShowBottomSheetChanged,
        dragHandle = null,
        containerColor = grey200,
        scrimColor = Color.Black.copy(0.6f),
        sheetState = bottomSheetState,
        modifier = modifier
    ) {

        AnimatedContent(
            targetState = currentSheet,
            transitionSpec = {
                fadeIn(animationSpec = tween(300)) + slideInHorizontally { it / 8 } togetherWith
                        fadeOut(animationSpec = tween(300)) + slideOutHorizontally { -it / 8 }

            },
            label = "BottomSheetTransition"
        ) { sheet ->
            when (sheet) {
                BottomSheetModals.ADD_CATEGORY ->
                    AddCategoryBottomSheetModal(
                        scope,
                        bottomSheetState,
                        onShowBottomSheetChanged,
                        inputState,
                        onNavigate = { onCurrentSheetChanged() },
                        onAddNewInfo
                    )

                BottomSheetModals.CREATE_NEW_CATEGORY ->
                    CreateCategoryBottomSheetModal(
                        inputState = inputState,
                        onSave = { onBack() },
                        onBack = { onBack() }
                    )

            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCategoryBottomSheetModal(
    scope: CoroutineScope,
    bottomSheetState: SheetState,
    onShowBottomSheetChanged: () -> Unit,
    inputState: CardInputState,
    onNavigate: () -> Unit,
    onAddNewInfo: (String, String, QuestionAndAnswer) -> Unit,
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

        Column(
            verticalArrangement = Arrangement.spacedBy(18.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                InputField(
                    userInput = inputState.categoryName,
                    onUserInputChanged = { newValue ->
                        inputState.onCategoryNameChanged(newValue)
                    },
                    label = "Category",
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(12.dp))


                FilledIconButton(
                    onClick = { onNavigate() },
                    shape = RoundedCornerShape(12.dp),
                    colors = IconButtonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black,
                        disabledContainerColor = Color.Transparent,
                        disabledContentColor = Color.Transparent
                    ),
                    interactionSource = remember { MutableInteractionSource() },
                    modifier = Modifier.size(56.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Add,
                        contentDescription = null,
                        modifier = Modifier
                            .size(28.dp)
                    )
                }
            }

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

        ExpandedInputField(
            userInput = inputState.answer,
            onUserInputChanged = { newValue ->
                inputState.onAnswerChanged(newValue)
            },
            label = "Answer to question"
        )
        Spacer(modifier = Modifier.height(36.dp))


    }

    ButtonComp(
        color = ButtonDefaults.buttonColors(bluePrimary),
        buttonText = "Add"
    ) {
        onAddNewInfo(
            inputState.categoryName,
            inputState.topic,
            QuestionAndAnswer(inputState.question, inputState.answer)
        )
        scope.launch {
            bottomSheetState.hide()
        }.invokeOnCompletion {
            if (!bottomSheetState.isVisible) {
                onShowBottomSheetChanged()
            }
        }
    }

    Spacer(modifier = Modifier.height(16.dp))
}
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateCategoryBottomSheetModal(
    inputState: CardInputState,
    onSave: (String) -> Unit,
    onBack: () -> Unit,
    label: String = "create category",
) {

    val icons = remember {
        listOf<Int>(
            R.drawable.binoculars,
            R.drawable.cable_car,
            R.drawable.car,
            R.drawable.bus,
            R.drawable.basket,
            R.drawable.visor,
            R.drawable.basketball,
        )
    }

    var selectedItem by remember{
        mutableIntStateOf(0)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.padding(24.dp)
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
                        onBack()
                    },
                    colors = IconButtonDefaults.iconButtonColors(Color.White),
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
                        contentDescription = "go back",
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

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            modifier = Modifier.padding(end = 16.dp)
        ) {
            items(icons) { icon ->
                CategoryIcon(
                    icon = icon,
                    selected = selectedItem == icon,
                    modifier = Modifier.clickable { selectedItem = icon }.clip(CircleShape)
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        InputField(
            userInput = inputState.categoryToAdd,
            onUserInputChanged = {
                inputState.onCategoryToAddChanged(it)
            },
            label = label
        )

        Spacer(modifier = Modifier.height(64.dp))

        ButtonComp(
            color = ButtonDefaults.buttonColors(bluePrimary),
            buttonText = "Save",
            onClick = {}
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}