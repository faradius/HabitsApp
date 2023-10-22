package com.alex.habitsapp.onboarding.presentation.screen

import androidx.compose.runtime.Composable
import com.alex.habitsapp.R
import com.alex.habitsapp.onboarding.presentation.components.OnboardingPager
import com.alex.habitsapp.onboarding.presentation.model.OnboardingPagerInformation

@Composable
fun OnboardingScreen(
    onFinish:() -> Unit
){
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
    
    OnboardingPager(pages = pages, onFinish = onFinish)
}