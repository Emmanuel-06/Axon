package com.example.axon.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.axon.dao.CategoryDao
import com.example.axon.dao.QuestionAndAnswerDao
import com.example.axon.dao.TopicDao
import com.example.axon.model.Category
import com.example.axon.model.QuestionAndAnswer
import com.example.axon.model.Topic

@Database(entities = [Category::class, Topic::class, QuestionAndAnswer::class ], version = 1)
abstract class AxonDatabase: RoomDatabase() {
    abstract fun categoryDao(): CategoryDao

    abstract fun topicDao(): TopicDao
    abstract fun questionAndAnswerDao(): QuestionAndAnswerDao
}