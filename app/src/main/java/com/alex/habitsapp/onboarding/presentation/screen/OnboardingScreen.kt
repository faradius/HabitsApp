package com.alex.habitsapp.onboarding.presentation.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.alex.habitsapp.R
import com.alex.habitsapp.onboarding.presentation.components.OnboardingPager
import com.alex.habitsapp.onboarding.presentation.model.OnboardingPagerInformation
import com.alex.habitsapp.onboarding.presentation.viewmodel.OnboardingViewModel

@Composable
fun OnboardingScreen(
    viewModel: OnboardingViewModel = hiltViewModel(),
    onFinish:() -> Unit
){
    LaunchedEffect(key1 = viewModel.hasSeenOnboarding){
        if (viewModel.hasSeenOnboarding){
            onFinish()
        }
    }

    val pages = listOf(
        OnboardingPagerInformation(
            title = "Welcome to\nMonumental Habits",
            subtitle = "We can help you to be a better version of yourself.",
            image = R.drawable.onboarding1
        ),
        OnboardingPagerInformation(
            title = "Create new\nhabit easily",
            subtitle = "We can help you to be a better version of yourself.",
            image = R.drawable.onboarding2
        ),
        OnboardingPagerInformation(
            title = "Keep track of your\nprogress",
            subtitle = "We can help you to be a better version of yourself.",
            image = R.drawable.onboarding3
        ),
        OnboardingPagerInformation(
            title = "Join a supportive\ncommunity",
            subtitle = "We can help you to be a better version of yourself.",
            image = R.drawable.onboarding4
        )

    )
    
    OnboardingPager(pages = pages, onFinish = {
        viewModel.completeOnboarding()
    })
}