package com.suraj854.healthquiz.data.signup.module

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import com.suraj854.healthquiz.data.common.UserPreferences
import com.suraj854.healthquiz.data.firebase.module.FirebaseModule
import com.suraj854.healthquiz.data.signup.repository.SignUpRepositoryIml
import com.suraj854.healthquiz.domain.signup.SignupRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module(includes = [FirebaseModule::class])
@InstallIn(SingletonComponent::class)
class SignUpModule {


    @Singleton
    @Provides
    fun provideUserPreference(@ApplicationContext context: Context) =
        UserPreferences(context)


    @Singleton
    @Provides
    fun provideSignUpRepository(
        firestore: FirebaseFirestore,
        userPreferences: UserPreferences
    ): SignupRepository {
        return SignUpRepositoryIml(
            firestore = firestore,
            userPreferences = userPreferences
        )
    }
}