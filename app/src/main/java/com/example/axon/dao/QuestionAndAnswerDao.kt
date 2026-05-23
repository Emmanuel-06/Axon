package com.example.axon.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.axon.model.QuestionAndAnswer
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestionAndAnswerDao {
    @Insert
    suspend fun insertQuestionAndAnswer(questionAndAnswer: QuestionAndAnswer)

    @Delete
    suspend fun deleteQuestionAndAnswer(questionAndAnswer: QuestionAndAnswer)

    @Query("SELECT * FROM questions_and_answers WHERE topicId= :topicId")
    fun getAllQuestionsAndAnswers(topicId: Int): Flow<List<QuestionAndAnswer>>

    @Query("SELECT * FROM questions_and_answers WHERE questionAndAnswerId = :questionAndAnswerId")
    fun getQuestionAndAnswerById(questionAndAnswerId: Int): Flow<QuestionAndAnswer>

}