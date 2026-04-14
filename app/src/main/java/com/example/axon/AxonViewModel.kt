package com.example.axon

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.axon.model.Category
import com.example.axon.model.QuestionAndAnswer
import com.example.axon.model.Topic


class AxonViewModel: ViewModel() {

    val categories = mutableStateListOf<Category>(
        Category(
            categoryName = "Kotlin",
            topics = mutableListOf(
                Topic(
                    topicName = "State Management",
                    questionAndAnswers = mutableListOf(
                        QuestionAndAnswer(
                            "What is a State?",
                            "A State is any value that can change in response to a users' action"
                        ),
                        QuestionAndAnswer(
                            "What is a State?",
                            "A State is any value that can change in response to a users' action"
                        ),
                    )
                ),
                Topic(
                    topicName = "Lambdas",
                    questionAndAnswers = mutableListOf(
                        QuestionAndAnswer(
                            "What is a lambda?",
                            "A lambda is an anonymous function..."
                        )
                    )
                ),
                Topic(
                    topicName = "Compose",
                    questionAndAnswers = mutableListOf(
                        QuestionAndAnswer(
                            "What is recomposition?",
                            "Recomposition is the process of re-rendering the composable functions (UI) to reflect a change in state"
                        )
                    )
                )
            )
        ),
        Category(
            categoryName = "Git",
            topics = mutableListOf(
                Topic(
                    topicName = "Git",
                    questionAndAnswers = mutableListOf(
                        QuestionAndAnswer(
                            "When does a Git conflict happen?",
                            "A Git conflict happens when ..."
                        )
                    )
                ),
                Topic(
                    topicName = "Version Control",
                    questionAndAnswers = mutableListOf(
                        QuestionAndAnswer(
                            "How does git as a version control system work?",
                            "It works by ..."
                        )
                    )
                )
            )
        ),

    )

    var currentIndex by mutableStateOf(0)
        private set

    fun addNewContent(categoryName: String, topicName: String, qa: QuestionAndAnswer){
        /* TODO: check for existing category logic */
        categories.add(
            Category(
                categoryName = categoryName,
                topics = mutableListOf(
                    Topic(
                        topicName,
                        mutableListOf(qa)
                    )
                )
            )
        )
    }

    fun nextQuestion(){
        if(currentIndex < categories.size -1){
            currentIndex++
        } else {
            return
        }
    }

    fun prevQuestion(){
        if(currentIndex > 0){
            currentIndex--
        } else {

        }
    }

}