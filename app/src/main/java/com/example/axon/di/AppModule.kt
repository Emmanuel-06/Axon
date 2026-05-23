package com.example.axon.di

import android.content.Context
import androidx.room.Room
import com.example.axon.dao.CategoryDao
import com.example.axon.dao.QuestionAndAnswerDao
import com.example.axon.dao.TopicDao
import com.example.axon.data.AxonDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providesCategoryDao(categoryDatabase: AxonDatabase): CategoryDao {
        return categoryDatabase.categoryDao()
    }

    @Provides
    @Singleton
    fun providesTopicDao(topicDatabase: AxonDatabase): TopicDao {
        return topicDatabase.topicDao()
    }

    @Provides
    @Singleton
    fun providesQuestionAndAnswerDao(questionAndAnswerDatabase: AxonDatabase): QuestionAndAnswerDao {
        return questionAndAnswerDatabase.questionAndAnswerDao()
    }


    @Provides
    @Singleton
    fun providesAppDatabase(@ApplicationContext context: Context): AxonDatabase{
        return Room.databaseBuilder(
            context,
            AxonDatabase::class.java,
            "category_database"
        ).fallbackToDestructiveMigration()
            .build()
    }
}