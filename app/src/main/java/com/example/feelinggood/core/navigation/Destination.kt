package com.example.feelinggood.core.navigation

sealed class Destination(
    val route: String
) {
    data object PreAuth : Destination(route = "PreAuth") {
        data object LoginScreen : Destination(route = "LoginScreen")
        data object RegistrationScreen : Destination(route = "RegistrationScreen")
    }

    data object PostAuth : Destination(route = "PostAuth") {
        data object HomeScreen : Destination(route = "HomeScreen")
    }
}