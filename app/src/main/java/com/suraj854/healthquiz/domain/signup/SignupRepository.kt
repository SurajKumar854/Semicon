package com.suraj854.healthquiz.domain.signup

import android.content.Context
import com.suraj854.healthquiz.data.signup.dto.SignUpResponse
import com.suraj854.healthquiz.domain.signup.entities.User
import kotlinx.coroutines.flow.Flow


interface SignupRepository {
    suspend fun signUp(context: Context,user: User): Flow<SignUpResponse>
    suspend fun storeUserPref(user: User)
}