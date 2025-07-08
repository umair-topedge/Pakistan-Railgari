package com.example.pakistanrailgari

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun SplashScreen(
    navigateNext: () -> Unit
) {
    var showProgress by remember { mutableStateOf(false) }
    var progress by remember { mutableFloatStateOf(0f) }

    SplashScreenContent(
        showProgress = showProgress,
        progress = progress
    )
}