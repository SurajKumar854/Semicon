package com.suraj854.healthquiz.data.quizbank.module

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import com.suraj854.healthquiz.data.common.UserPreferences
import com.suraj854.healthquiz.data.firebase.module.FirebaseModule
import com.suraj854.healthquiz.data.quizbank.manager.RandomQuizGroupManager
import com.suraj854.healthquiz.data.quizbank.repository.QuizRepositoryIml
import com.suraj854.healthquiz.domain.quiz.QuizRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module(includes = [FirebaseModule::class])
@InstallIn(SingletonComponent::class)
class QuizModule {


    @Singleton
    @Provides
    fun provideRandomQuizGroupManager(@ApplicationContext context: Context) =
        RandomQuizGroupManager(context)


    @Singleton
    @Provides
    fun provideQuizRepository(
        firestore: FirebaseFirestore,
        userPreferences: UserPreferences
    ): QuizRepository {
        return QuizRepositoryIml(
            firestore = firestore,
            userPreferences = userPreferences
        )
    }
}