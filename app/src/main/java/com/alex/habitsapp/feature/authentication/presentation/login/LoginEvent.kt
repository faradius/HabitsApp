package com.alex.habitsapp.feature.authentication.presentation.login

sealed interface LoginEvent {
    data class EmailChanged(val email: String) : LoginEvent
    data class PasswordChanged(val password: String) : LoginEvent
    object Login: LoginEvent
}