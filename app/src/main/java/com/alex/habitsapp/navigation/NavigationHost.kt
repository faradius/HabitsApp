package com.alex.habitsapp.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.alex.habitsapp.feature.authentication.presentation.login.LoginScreen
import com.alex.habitsapp.feature.onboarding.presentation.screen.OnboardingScreen

@Composable
fun NavigationHost(
    navHostController: NavHostController,
    startDestination: NavigationRoute
) {
    NavHost(navController = navHostController, startDestination = startDestination.route) {
        composable(NavigationRoute.Onboarding.route) {
            OnboardingScreen(onFinish = {
                navHostController.popBackStack()
                navHostController.navigate(NavigationRoute.Login.route)
            },

            )
        }
        
        composable(NavigationRoute.Login.route){
            LoginScreen(
                onLogin = {
                    navHostController.popBackStack()
                    navHostController.navigate(NavigationRoute.Home.route)
                }, onSignUp = {
                    navHostController.navigate(NavigationRoute.SignUp.route)
                }
            )

        }

        composable(NavigationRoute.SignUp.route){
            Text(text = "Estas en signup")

        }

        composable(NavigationRoute.Home.route){
            Text(text = "Estas en home")

        }
    }
}