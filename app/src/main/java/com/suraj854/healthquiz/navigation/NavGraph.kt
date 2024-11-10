package com.tres.homes.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.suraj854.healthquiz.navigation.Screen
import com.suraj854.healthquiz.presentation.qr_code.QRCodeScreen
import com.suraj854.healthquiz.presentation.quiz.QuizScreen
import com.suraj854.healthquiz.presentation.signup.screen.SignUpScreen
import com.suraj854.healthquiz.presentation.splash.screen.SplashScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.SplashScreen.route
    ) {
        composable(Screen.SplashScreen.route) {
            SplashScreen(navController = navController)

        }

        composable(Screen.SignUpScreen.route) {
            SignUpScreen(navHostController = navController)
        }

        composable(Screen.QuizScreen.route) {
            QuizScreen(navHostController = navController)
        }
        composable(Screen.QRCodeScreen.route) {
            QRCodeScreen(navHostController = navController)
        }


    }

}