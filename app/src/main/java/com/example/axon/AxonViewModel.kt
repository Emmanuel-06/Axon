package com.example.axon

import androidx.compose.material3.Icon
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.axon.model.Category
import com.example.axon.model.DropDownMenuItem
import com.example.axon.model.QuestionAndAnswer
import com.example.axon.model.Topic


class AxonViewModel: ViewModel() {

    val categories = mutableStateListOf<Category>()

    val categoriesCreated = mutableStateListOf<DropDownMenuItem>()

    var currentIndex by mutableStateOf(0)
        private set

    fun addNewContent(icon: Int, categoryName: String, topicName: String, qa: QuestionAndAnswer){
        /* TODO: check for existing category logic */
        categories.add(
            Category(
                icon = icon,
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

    fun createNewCategory(categoryIcon: Int, categoryName: String){
        categoriesCreated.add(
            DropDownMenuItem(
                icon = categoryIcon,
                title = categoryName
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