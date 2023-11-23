package com.alex.habitsapp.feature.onboarding.presentation.model

import androidx.annotation.DrawableRes

data class OnboardingPagerInformation(
    val title:String,
    val subtitle:String,
    @DrawableRes val image:Int
)
