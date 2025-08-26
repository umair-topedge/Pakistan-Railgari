package com.example.pakistanrailgari

import kotlinx.serialization.Serializable

@Serializable
object Routes {

    @Serializable
    data object SplashScreenRoutes

    @Serializable
    data object MainScreenRoutes
    
    @Serializable
    data object AlarmScreenRoutes

}