package com.example.axon.repository

import com.example.axon.dao.TopicDao
import com.example.axon.model.Topic
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TopicRepository @Inject constructor(
    private val topicDao: TopicDao
) {
    suspend fun insertTopic(topic: Topic): Long {
         return topicDao.insertTopic(topic)
    }

    suspend fun deleteTopic(topic: Topic){
        topicDao.deleteTopic(topic)
    }

    suspend fun updateTopic(topic: Topic){
        topicDao.updateTopic(topic)
    }

    fun getAllTopics(categoryName: String): Flow<List<Topic>> {
        return topicDao.getAllTopics(categoryName)
    }


}