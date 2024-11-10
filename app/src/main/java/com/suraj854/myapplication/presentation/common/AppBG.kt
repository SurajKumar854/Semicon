package com.suraj854.myapplication.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun GradientBackgroundScreen() {
    val gradientBrush = Brush.linearGradient(
        colors = listOf(
            Color(0xFF54F3BC), // Color #54F3BC
            Color(0xFF9EA1FF), // Color #9EA1FF
            Color(0xFFFFFFFF)  // Color #FFFFFF
        )
    )

    Modifier.background(brush = gradientBrush)
        .fillMaxSize()
}