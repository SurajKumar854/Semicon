package com.suraj854.healthquiz.data.signup.dto

import com.suraj854.healthquiz.data.signup.enum.AuthErrorType

data class SignUpResponse(
    val status: Boolean,
    val errorType: AuthErrorType=AuthErrorType.NoError

)
