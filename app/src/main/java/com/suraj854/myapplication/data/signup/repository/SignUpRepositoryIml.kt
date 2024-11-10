package com.suraj854.myapplication.data.signup.repository

import android.content.Context
import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.suraj854.myapplication.data.common.UserPreferences
import com.suraj854.myapplication.data.signup.dto.SignUpResponse
import com.suraj854.myapplication.data.signup.enum.AuthErrorType
import com.suraj854.myapplication.domain.signup.entities.User
import com.suraj854.myapplication.domain.signup.SignupRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.coroutines.resume

class SignUpRepositoryIml @Inject constructor(
    val firestore: FirebaseFirestore,
    val userPreferences: UserPreferences
) : SignupRepository {

    override suspend fun signUp(context: Context, user: User): Flow<SignUpResponse> = flow {
        // Check if email exists
        val emailExists = firestore.collection("users")
            .whereEqualTo("email", user.email).get().await()
            .isEmpty

        if (!emailExists) {
            emit(SignUpResponse(status = false, errorType = AuthErrorType.Email))
            return@flow
        }

        // Check if phone exists
        val phoneExists = firestore.collection("users")
            .whereEqualTo("phone", user.phone)
            .whereEqualTo("country_code", user.country_code).get().await()
            .isEmpty

        if (!phoneExists) {
            emit(SignUpResponse(status = false, errorType = AuthErrorType.MobileNumber))
            return@flow
        }

        // If email and phone are both available, add user
        firestore.collection("users").add(user).await()
        storeUserPref(user = user)

        emit(SignUpResponse(status = true, errorType = AuthErrorType.NoError))
    }

    override suspend fun storeUserPref(user: User) {
        userPreferences.saveUserData(
            firstName = user.firstname,
            lastName = user.lastname,
            email = user.email,
            countryCode = user.country_code,
            phoneNumber = user.phone
        )

    }
}


