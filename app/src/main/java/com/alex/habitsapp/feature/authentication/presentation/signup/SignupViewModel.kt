package com.alex.habitsapp.feature.authentication.presentation.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class SignupViewModel @Inject constructor(): ViewModel() {
    var state by mutableStateOf(SignupState())
        private set

    fun onEvent(event: SignupEvent) {
        when (event) {
            is SignupEvent.EmailChanged -> {
                state = state.copy(email = event.email)
            }
            is SignupEvent.PasswordChanged -> {
                state = state.copy(password = event.password)
            }
            SignupEvent.SignIn -> {
                state = state.copy(signIn = true)
            }
            SignupEvent.SignUp -> {
                signUp()
            }
        }
    }

    private fun signUp() {

    }
}