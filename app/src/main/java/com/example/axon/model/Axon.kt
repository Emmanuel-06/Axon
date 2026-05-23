package com.example.axon.model


import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "categories")
data class Category(
    @PrimaryKey
    val categoryName: String,
    val icon: Int,
)

@Entity(tableName = "topics")
data class Topic(
    @PrimaryKey(autoGenerate = true)
    val topicId: Int = 0,
    val topicName: String,
    val categoryName: String
)

@Entity(tableName = "questions_and_answers")
data class QuestionAndAnswer(
    @PrimaryKey(autoGenerate = true)
    val questionAndAnswerId: Int = 0,
    val question: String,
    val answer: String,
    val topicId: Int
)


