package com.example.axon.model

import com.example.axon.R

//data class Categories(
//    val icon: Int = R.drawable.android_studio_icon,
//    val categoryName: String,
//    val topic: String,
//    val content: QuestionAndAnswer
//)
//
//data class QuestionAndAnswer(
//    val question: String,
//    val answer: String
//)

data class Category(
    val icon: Int = R.drawable.android_studio_icon,
    val categoryName: String,
    val topics: MutableList<Topic> = mutableListOf()
)

data class Topic(
    val topicName: String,
    val questionAndAnswers: MutableList<QuestionAndAnswer> = mutableListOf()
)

data class QuestionAndAnswer(
    val question: String,
    val answer: String
)
