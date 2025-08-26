package com.example.pakistanrailgari

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pakistanrailgari.alarm.ui.AlarmScreen

@Composable
fun PakistanRailgariApp(navController: NavHostController) {
    val appDestinations = remember(navController) {
        AppDestinations(navController)
    }

    NavHost(navController = navController, startDestination = Routes.SplashScreenRoutes) {
        composable<Routes.SplashScreenRoutes> {
            SplashScreen(navigateNext = { appDestinations.navigateToAlarmScreen() })
        }

        composable<Routes.MainScreenRoutes> {
            MainScreen(
                onBackPress = { appDestinations.navigateToBackPress },
                onNavigateToAlarm = { appDestinations.navigateToAlarmScreen() }
            )
        }
        
        composable<Routes.AlarmScreenRoutes> {
            AlarmScreen()
        }
    }
}