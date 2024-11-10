package com.suraj854.healthquiz.data.firebase.module

import android.content.Context
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
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