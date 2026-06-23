package com.example.axon.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.axon.model.Topic
import kotlinx.coroutines.flow.Flow

@Dao
interface TopicDao {

    @Insert
    suspend fun insertTopic(topic: Topic): Long

    @Update
    suspend fun updateTopic(topic: Topic)

    @Delete
    suspend fun deleteTopic(topic: Topic)

    @Query("SELECT * FROM topics WHERE categoryName = :categoryName")
    fun getAllTopics(categoryName: String): Flow<List<Topic>>

    @Query("SELECT topicName FROM topics WHERE topicId = :topicId")
    fun getTopicNameById(topicId: Int): String

    @Query("SELECT * FROM topics WHERE topicId = :topicId")
    fun getTopicById(topicId: Int): Flow<Topic>
}