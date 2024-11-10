package com.suraj854.myapplication.data.firebase.module

import android.content.Context
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.suraj854.myapplication.data.common.UserPreferences
import com.suraj854.myapplication.data.quizbank.manager.RandomQuizGroupManager
import com.suraj854.myapplication.data.signup.repository.SignUpRepositoryIml
import com.suraj854.myapplication.domain.quiz.QuizRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module(includes = [])
@InstallIn(SingletonComponent::class)
class FirebaseModule {

    @Singleton
    @Provides
    fun provideFireStore(@ApplicationContext context: Context) =
        Firebase.firestore

}