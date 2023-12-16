package com.alex.habitsapp.feature.authentication.presentation.login

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alex.habitsapp.feature.authentication.domain.repository.AuthenticationRepository
import com.alex.habitsapp.feature.authentication.domain.usecase.LoginUseCases
import com.alex.habitsapp.feature.authentication.domain.usecase.LoginWithEmailUseCase
import com.alex.habitsapp.feature.authentication.domain.usecase.PasswordResult
import com.alex.habitsapp.feature.authentication.domain.usecase.ValidateEmailUseCase
import com.alex.habitsapp.feature.authentication.domain.usecase.ValidatePasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCases: LoginUseCases
):ViewModel() {
    var state by mutableStateOf(LoginState())
    private set

    fun onEvent(event: LoginEvent){
        when(event){
            is LoginEvent.EmailChanged -> {
                state = state.copy(email = event.email)
            }
            LoginEvent.Login -> login()
            is LoginEvent.PasswordChanged -> {
                state = state.copy(password = event.password)
            }
            LoginEvent.SignUp -> {
                state = state.copy(signUp = true)
            }
        }
    }

    private fun login(){
        state = state.copy(emailError = null, passwordError = null)

        if (!loginUseCases.validateEmailUseCase(state.email)){
            state = state.copy(emailError = "El email no es valido")
        }

        val passwordResult = loginUseCases.validatePasswordUseCase(state.password)
        if(passwordResult is PasswordResult.Invalid){
            state = state.copy(passwordError = passwordResult.errorMessage)
        }

        if (state.emailError == null && state.passwordError == null){

            state = state.copy(isLoading = true)


            viewModelScope.launch {
                loginUseCases.loginWithEmailUseCase(state.email, state.password).onSuccess {
                    state = state.copy(isLoggedIn = true)
                }.onFailure {
                    state = state.copy(passwordError = it.message)
                }
                state = state.copy(isLoading = false)
            }
        }

    }
}