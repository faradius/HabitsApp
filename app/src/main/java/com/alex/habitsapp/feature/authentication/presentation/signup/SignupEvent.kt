package com.alex.habitsapp.feature.authentication.presentation.signup

sealed interface SignupEvent {
    data class EmailChanged(val email: String) : SignupEvent
    data class PasswordChanged(val password: String) : SignupEvent
    object SignIn: SignupEvent
    object SignUp: SignupEvent
}