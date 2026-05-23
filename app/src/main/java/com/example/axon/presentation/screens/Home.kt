package com.example.axon.presentation.screens

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.axon.AxonViewModel
import com.example.axon.R
import com.example.axon.components.ButtonComp
import com.example.axon.components.CategoryCard
import com.example.axon.components.CategoryIcon
import com.example.axon.components.DropDownInputField
import com.example.axon.components.ExpandedInputField
import com.example.axon.components.InputField
import com.example.axon.model.CardInputState
import com.example.axon.model.Category
import com.example.axon.model.DropDownMenuItem
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
    axonViewModel: AxonViewModel,
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

    val error by axonViewModel.error.collectAsState()
    val listOfCategories by axonViewModel.categories.collectAsState()
    val topics by axonViewModel.topics.collectAsState()
    val questionsAndAnswers by axonViewModel.questionsAndAnswers.collectAsState()


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
                colors = TopAppBarDefaults.topAppBarColors(Color.Transparent),
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
        },
        containerColor = grey200.copy(alpha = 0.6f)
    ) { paddingValues ->

        if(listOfCategories.isEmpty()){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    painter = painterResource(R.drawable.empty_state),
                    contentDescription = null,
                    modifier = Modifier
                        .size(160.dp)
                        .alpha(0.4f)
                )
                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = "Add a Category to begin",
                    fontFamily = ttHovesFontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    color = Color.Black.copy(alpha = 0.3f)
                )
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                items(listOfCategories) { category ->
                    CategoryCard(
                        category = category,
                        onClick = {
                            axonViewModel.selectCategory(category.categoryName)
                            onCategoryClick(category.categoryName)
                        }
                    )
                }
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
                viewModel = axonViewModel,
                onClear = {
                          categoryToAddState = ""
                },
                duplicateCategoryErrorMessage = error,
                onAddNewInfo = { categoryName, topicName, question, answer ->
                    axonViewModel.addNewContent(
                        categoryName,
                        topicName,
                        question,
                        answer
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
    viewModel: AxonViewModel,
    onClear: ()-> Unit,
    duplicateCategoryErrorMessage: String,
    onAddNewInfo: (String, String, String, String) -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = onShowBottomSheetChanged,
        dragHandle = null,
        containerColor = Color.Transparent,
        scrimColor = Color.Black.copy(0.6f),
        sheetState = bottomSheetState,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .background(grey200, RoundedCornerShape(24.dp)),
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
                            onAddNewInfo,
                            viewModel
                        )

                    BottomSheetModals.CREATE_NEW_CATEGORY ->
                        CreateCategoryBottomSheetModal(
                            inputState = inputState,
                            onSave = { icon, name ->
                                     viewModel.createANewCategory(name, icon)
                                 },
                            onBack = { onBack() },
                            onClear = {onClear()},
                            errorMessage = duplicateCategoryErrorMessage
                        )

                }
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
    onAddNewInfo: (String, String, String, String) -> Unit,
    viewModel: AxonViewModel,
) {

    var expanded by remember {
        mutableStateOf(false)
    }
    var selectedItem by remember {
        mutableStateOf(DropDownMenuItem(R.drawable.bus, ""))
    }
    val rotation by animateFloatAsState(
        targetValue = if(expanded) 180f else 0f,
        label = ""
    )

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
                        contentDescription = "close the bottom sheet",
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
            // ExposedDropDown Menu Box
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = {
                    expanded = !expanded
                }
            ) {
                Row(
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    DropDownInputField(
                        userInput = selectedItem.title,
                        onUserInputChanged = {},
                        label = "Category",
                        readOnly = true,
                        modifier = Modifier
                            .weight(1f)
                            .menuAnchor(),
                        leadingIcon = {
                                      CategoryIcon(icon = selectedItem.icon, selected = false, modifier = Modifier
                                          .size(30.dp))
                        },
                        trailingIcon = {
                            IconButton(
                                onClick = {
                                    expanded = !expanded
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.KeyboardArrowDown,
                                    contentDescription = null,
                                    tint = Color.Black.copy(alpha = 0.6f),
                                    modifier = Modifier
                                        .size(28.dp)
                                        .rotate(rotation)
                                )

                            }
                        }
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

                        )
                    }
                }

                // Exposed Dropdown Menu
                MaterialTheme(
                    colorScheme = MaterialTheme.colorScheme.copy(surfaceTint = Color.White),
                    shapes = MaterialTheme.shapes.copy(extraSmall = RoundedCornerShape(16.dp))
                ){
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier
                            .clip(RoundedCornerShape(24.dp))
                            .padding(0.dp)
                    ) {
                        viewModel.categories.value.forEachIndexed { index, item ->
                            DropdownMenuItem(
                                text = {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                                    ) {
                                        CategoryIcon(
                                            icon = item.icon,
                                            selected = false,
                                            modifier = Modifier.size(38.dp)
                                        )

                                        Text(
                                            text = item.categoryName,
                                            fontFamily = ttHovesFontFamily,
                                            fontSize = 18.sp
                                        )
                                    }
                                },
                                onClick = {
                                    selectedItem = DropDownMenuItem(item.icon, item.categoryName)
                                    expanded = false
                                }
                            )

                        }

                    }
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
                selectedItem.title,
                inputState.topic,
                inputState.question,
                inputState.answer
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


@SuppressLint("UnrememberedMutableInteractionSource")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateCategoryBottomSheetModal(
    inputState: CardInputState,
    onSave: (newCategoryIconRes: Int, newCategoryTitle: String ) -> Boolean,
    onBack: () -> Unit,
    onClear: () -> Unit,
    errorMessage: String,
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

    var selectedIndex by remember {
        mutableIntStateOf(0)
    }

    val interactionSource = remember{
        MutableInteractionSource()
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
                text = "Create a new Category",
                fontFamily = ttHovesFontFamily,
                fontSize = 16.sp,
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
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(end = 16.dp)
        ) {
            items(icons.size) { index ->
                CategoryIcon(
                    icon = icons[index],
                    selected = selectedIndex == index,
                    modifier = Modifier
                        .clickable(
                            interactionSource = interactionSource, indication = null,
                            onClick = { selectedIndex = index }
                        )

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

        Spacer(modifier = Modifier.weight(1f))

        ButtonComp(
            color = ButtonDefaults.buttonColors(bluePrimary),
            buttonText = "Save",
            onClick = {
                val result = onSave(icons[selectedIndex], inputState.categoryToAdd)
                if(result){
                    onBack()
                }
                onClear()
            },
        )
        Spacer(modifier = Modifier.height(12.dp))

        if(errorMessage.isNotEmpty()){
            Text(
                text = errorMessage,
                color = Color.Red
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}