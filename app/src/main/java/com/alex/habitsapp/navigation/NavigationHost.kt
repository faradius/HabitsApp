package com.alex.habitsapp.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.alex.habitsapp.feature.authentication.presentation.login.LoginScreen
import com.alex.habitsapp.feature.authentication.presentation.signup.SignupScreen
import com.alex.habitsapp.feature.home.presentation.home.HomeScreen
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
            SignupScreen(
                onSignIn = {
                    navHostController.navigate(NavigationRoute.Home.route){
                        popUpTo(navHostController.graph.id){
                            inclusive = true
                        }
                    }
                }, onLogin = {
                    navHostController.popBackStack()
                }
            )

        }

        composable(NavigationRoute.Home.route){
            HomeScreen()
        }
    }
}