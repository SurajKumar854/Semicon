package com.suraj854.myapplication.data.signup.dto

import com.google.rpc.context.AttributeContext.Auth
import com.suraj854.myapplication.data.quizbank.dto.QuizBankData
import com.suraj854.myapplication.data.signup.enum.AuthErrorType

data class SignUpResponse(
    val status: Boolean,
    val errorType: AuthErrorType=AuthErrorType.NoError

)
