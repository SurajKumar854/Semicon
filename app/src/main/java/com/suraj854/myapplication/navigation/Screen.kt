package com.suraj854.myapplication.navigation

import com.suraj854.myapplication.navigation.Routes.QRCODE_SCREEN
import com.suraj854.myapplication.navigation.Routes.QUIZ_SCREEN
import com.suraj854.myapplication.navigation.Routes.REGISTER_SCREEN
import com.suraj854.myapplication.navigation.Routes.SPLASH_SCREEN

sealed class Screen(val route: String) {
    data object SplashScreen : Screen(SPLASH_SCREEN)
    data object SignUpScreen : Screen(REGISTER_SCREEN)
    data object QuizScreen : Screen(QUIZ_SCREEN)
    data object QRCodeScreen : Screen(QRCODE_SCREEN)
}