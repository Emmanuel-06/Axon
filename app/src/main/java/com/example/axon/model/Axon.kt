package com.example.axon.model


import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(tableName = "categories")
data class Category(
    @PrimaryKey
    val categoryName: String,
    val icon: Int,
)

@Entity(
    tableName = "topics",
    foreignKeys = [
        ForeignKey(
            entity = Category::class,
            parentColumns = ["categoryName"],
            childColumns = ["categoryName"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Topic(
    @PrimaryKey(autoGenerate = true)
    val topicId: Int = 0,
    val topicName: String,
    val categoryName: String
)

@Entity(
    tableName = "questions_and_answers",
    foreignKeys = [
        ForeignKey(
            entity = Topic::class,
            parentColumns = ["topicId"],
            childColumns = ["topicId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class QuestionAndAnswer(
    @PrimaryKey(autoGenerate = true)
    val questionAndAnswerId: Int = 0,
    val question: String,
    val answer: String,
    val topicId: Int
)


