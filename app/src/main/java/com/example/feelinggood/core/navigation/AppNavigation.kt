package com.example.feelinggood.core.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.feelinggood.auth.StartDestinationViewModel
import com.example.feelinggood.auth.ui.login_screen.LoginScreen
import com.example.feelinggood.auth.ui.login_screen.LoginScreenViewModel
import com.example.feelinggood.auth.ui.registration_screen.RegistrationScreen
import com.example.feelinggood.auth.ui.registration_screen.RegistrationScreenViewModel

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    startDestinationViewModel: StartDestinationViewModel,
    registrationScreenViewModel: RegistrationScreenViewModel,
    loginScreenViewModel: LoginScreenViewModel,
    navController: NavHostController = rememberNavController(),
) {
    val startDestination by startDestinationViewModel._startDestination.collectAsState()

    NavHost(navController = navController, startDestination = startDestination.route) {
        navigation(
            route = Destination.PreAuth.route,
            startDestination = Destination.PreAuth.LoginScreen.route
        ) {
            /**
             * Login Screen
             */
            composable(route = Destination.PreAuth.LoginScreen.route) {
                LoginScreen(
                    modifier = modifier.fillMaxSize(),
                    navigateToRegistrationScreen = {
                        navigateAndPopUp(
                            desiredDestinationId = Destination.PreAuth.RegistrationScreen.route,
                            popUpDestinationId = Destination.PreAuth.LoginScreen.route,
                            navController = navController
                        )
                    },
                    loginScreenViewModel = loginScreenViewModel
                )
            }

            /**
             * Registration Screen
             */
            composable(route = Destination.PreAuth.RegistrationScreen.route) {
                RegistrationScreen(
                    modifier = modifier.fillMaxSize(),
                    navigateToLoginScreen = {
                        navigateAndPopUp(
                            desiredDestinationId = Destination.PreAuth.LoginScreen.route,
                            popUpDestinationId = Destination.PreAuth.RegistrationScreen.route,
                            navController = navController
                        )
                    },
                    registrationScreenViewModel = registrationScreenViewModel
                )
            }
        }
    }
}

fun navigateAndPopUp(
    desiredDestinationId: String,
    popUpDestinationId: String,
    navController: NavHostController
) {
    navController.navigate(desiredDestinationId) {
        launchSingleTop = true
        popUpTo(popUpDestinationId) { inclusive = true }
    }
}