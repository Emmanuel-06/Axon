package com.example.axon.repository

import com.example.axon.dao.QuestionAndAnswerDao
import com.example.axon.model.QuestionAndAnswer
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class QuestionAndAnswerRepository @Inject constructor(
    private val questionAndAnswerDao: QuestionAndAnswerDao
) {
    suspend fun insertQuestionAndAnswer(questionAndAnswer: QuestionAndAnswer){
        questionAndAnswerDao.insertQuestionAndAnswer(questionAndAnswer)
    }

    suspend fun deleteQuestionAndAnswer(questionAndAnswer: QuestionAndAnswer){
        questionAndAnswerDao.deleteQuestionAndAnswer(questionAndAnswer)
    }

    fun getAllQuestionsAndAnswers(topicId: Int): Flow<List<QuestionAndAnswer>> {
        return questionAndAnswerDao.getAllQuestionsAndAnswers(topicId)
    }

}