package com.example.pakistanrailgari

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navigateNext: () -> Unit
) {
    var showProgress by remember { mutableStateOf(false) }
    var progress by remember { mutableFloatStateOf(0f) }

    LaunchedEffect(Unit) {
        // Simulate loading
        showProgress = true
        while (progress < 1f) {
            delay(10)
            progress += 0.01f
        }
        // Navigate to alarm screen after splash
        delay(500)
        navigateNext()
    }

    SplashScreenContent(
        showProgress = showProgress,
        progress = progress
    )
}