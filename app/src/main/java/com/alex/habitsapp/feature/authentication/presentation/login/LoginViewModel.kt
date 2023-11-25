package com.alex.habitsapp.feature.authentication.presentation.login

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alex.habitsapp.feature.authentication.domain.repository.AuthenticationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
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
        viewModelScope.launch {
            authenticationRepository.login(state.email, state.password).onSuccess {
                Log.d("TAG", "login: Success")
            }.onFailure {
                val error = it.message
                Log.d("TAG", "login: $error")
            }
        }
    }
}