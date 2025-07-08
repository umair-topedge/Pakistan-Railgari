package com.example.pakistanrailgari

import kotlinx.serialization.Serializable

sealed class Routes {

    @Serializable
    data object SplashScreenRoutes : Routes()

    @Serializable
    data object MainScreenRoutes : Routes()
}