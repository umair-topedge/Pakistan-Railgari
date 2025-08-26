package com.example.pakistanrailgari

import androidx.navigation.NavController

class AppDestinations(
    private val navController: NavController,
) {

    val navigateToBackPress: () -> Unit = {
        navController.popBackStack()
    }

    val navigateToMainScreen: () -> Unit = {
        navController.navigate(Routes.MainScreenRoutes) {
            popUpTo(Routes.SplashScreenRoutes) { inclusive = true }
        }
    }
    
    val navigateToAlarmScreen: () -> Unit = {
        navController.navigate(Routes.AlarmScreenRoutes)
    }
}