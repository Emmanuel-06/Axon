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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.function.BiPredicate


class AxonViewModel : ViewModel() {

    val categories = mutableStateListOf<Category>()

    val categoriesCreated = mutableStateListOf<DropDownMenuItem>()

    val _error = MutableStateFlow<String>("")
    val error = _error.asStateFlow()


    var currentIndex by mutableStateOf(0)
        private set

    fun addNewContent(icon: Int, categoryName: String, topicName: String, qa: QuestionAndAnswer) {
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

    fun createNewCategory(categoryIcon: Int, categoryName: String): Boolean {
        if(categoryName.isEmpty()){
            _error.value = "Category name is empty"
            return false
        } else if (categoriesCreated.any {
                it.title.equals(categoryName, ignoreCase = true)
            }
        ) {
            _error.value = "Category already exists"
            return false
        }
        else {
            categoriesCreated.add(
                DropDownMenuItem(
                    icon = categoryIcon,
                    title = categoryName
                )
            )
            _error.value = ""
            return true
        }
    }


    fun nextQuestion() {
        if (currentIndex < categories.size - 1) {
            currentIndex++
        } else {
            return
        }
    }

    fun prevQuestion() {
        if (currentIndex > 0) {
            currentIndex--
        } else {

        }
    }

}