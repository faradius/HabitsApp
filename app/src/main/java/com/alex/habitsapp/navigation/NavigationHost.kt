package com.alex.habitsapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.alex.habitsapp.onboarding.presentation.screen.OnboardingScreen

@Composable
fun NavigationHost(
    navHostController: NavHostController,
    startDestination: NavigationRoute
) {
    NavHost(navController = navHostController, startDestination = startDestination.route) {
        composable(NavigationRoute.Onboarding.route) {
            OnboardingScreen(onFinish = {
                println("Termino el Onboarding")
            })
        }
    }
}