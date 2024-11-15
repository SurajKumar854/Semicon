package com.suraj854.myapplication.domain.signup.usecase

import android.content.Context
import com.suraj854.myapplication.data.signup.dto.SignUpResponse
import com.suraj854.myapplication.domain.signup.entities.User
import com.suraj854.myapplication.domain.signup.SignupRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignUpUseCase @Inject constructor(private val signupRepository: SignupRepository) {
    suspend fun executeSignUp(context: Context,user: User): Flow<SignUpResponse?> {
        return signupRepository.signUp(context, user = user)
    }
}