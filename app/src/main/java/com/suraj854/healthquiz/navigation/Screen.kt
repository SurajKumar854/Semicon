package com.suraj854.healthquiz.navigation

import com.suraj854.healthquiz.navigation.Routes.QRCODE_SCREEN
import com.suraj854.healthquiz.navigation.Routes.QUIZ_SCREEN
import com.suraj854.healthquiz.navigation.Routes.REGISTER_SCREEN
import com.suraj854.healthquiz.navigation.Routes.SPLASH_SCREEN

sealed class Screen(val route: String) {
    data object SplashScreen : Screen(SPLASH_SCREEN)
    data object SignUpScreen : Screen(REGISTER_SCREEN)
    data object QuizScreen : Screen(QUIZ_SCREEN)
    data object QRCodeScreen : Screen(QRCODE_SCREEN)
}