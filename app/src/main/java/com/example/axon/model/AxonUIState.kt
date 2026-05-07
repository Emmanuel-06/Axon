package com.example.axon.model

data class CardInputState(
    val categoryName: String,
    val categoryToAdd: String,
    val question: String,
    val answer: String,
    val topic: String,
    val onCategoryNameChanged: (String) -> Unit,
    val onCategoryToAddChanged: (String) -> Unit,
    val onQuestionChanged: (String) -> Unit,
    val onAnswerChanged: (String) -> Unit,
    val onTopicChanged: (String) -> Unit
)