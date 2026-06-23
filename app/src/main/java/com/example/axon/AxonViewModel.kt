package com.example.axon

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.axon.model.Category
import com.example.axon.model.QuestionAndAnswer
import com.example.axon.model.Topic
import com.example.axon.repository.CategoryRepository
import com.example.axon.repository.QuestionAndAnswerRepository
import com.example.axon.repository.TopicRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AxonViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val topicRepository: TopicRepository,
    private val questionAndAnswerRepository: QuestionAndAnswerRepository
) : ViewModel() {

    private var _error = MutableStateFlow<String>("")
    val error = _error.asStateFlow()

    private var _currentIndex = MutableStateFlow(0)
    val currentIndex = _currentIndex.asStateFlow()



    //tells the viewModel which category and topic were clicked so it can fetch the corresponding data using flatMapLatest
    private val _selectedCategoryName = MutableStateFlow("")
    private val _selectedTopicId = MutableStateFlow(0)


    val categories = categoryRepository.getAllCategories()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())


    val topics = _selectedCategoryName
        .flatMapLatest { categoryName ->
            topicRepository.getAllTopics(categoryName)
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())


    val questionsAndAnswers = _selectedTopicId
        .flatMapLatest { topicId ->
            questionAndAnswerRepository.getAllQuestionsAndAnswers(topicId)
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun createANewCategory(categoryName: String, categoryIcon: Int): Boolean {
        if(categoryName.isEmpty()){
            _error.value = "Name cannot be blank"
            return false
        } else if (categories.value.any { it.categoryName.equals(categoryName, ignoreCase = true)}){
            _error.value = "Category name already exists"
            return false
        } else {
            viewModelScope.launch {
                categoryRepository.insertCategory(
                    Category(
                        categoryName,
                        categoryIcon
                    )
                )
            }
            return true
        }
    }

    fun addNewContent(categoryName: String, topicName: String, question: String, answer: String){
        viewModelScope.launch {
            val topicId = topicRepository.insertTopic(
                Topic(
                    topicName = topicName,
                    categoryName = categoryName     //foreign key to establish Parent -> Child relationship between the Category selected and the child list (topics) displayed
                )
            )
            questionAndAnswerRepository.insertQuestionAndAnswer(
                QuestionAndAnswer(
                    question = question,
                    answer = answer,
                    topicId = topicId.toInt()     //foreign key to establish Parent -> Child relationship between the Topic selected and the child list (questions and answers) displayed
                )
            )
        }
    }

    fun selectCategory(categoryName: String){
        _selectedCategoryName.value = categoryName
    }

    fun selectTopic(topicId: Int){
        _selectedTopicId.value = topicId
        _currentIndex.value = 0
    }

    fun resetError(){
        _error.value = ""
    }

    fun deleteCategory(category: Category){
        viewModelScope.launch {
            categoryRepository.deleteCategory(category)
        }
    }

    fun nextQuestion() {
        if (_currentIndex.value < questionsAndAnswers.value.size - 1) {
            _currentIndex.value++
        } else {
            return
        }
    }

    fun prevQuestion() {
        if (_currentIndex.value > 0) {
            _currentIndex.value--
        } else {

        }
    }

}