package com.alex.habitsapp.feature.authentication.data.repository

import com.alex.habitsapp.feature.authentication.domain.repository.AuthenticationRepository
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class AuthenticationRepositoryImpl : AuthenticationRepository {
    override suspend fun login(email: String, password: String): Result<Unit> {
        return try {
            //Firebase no soporta corrutinas por lo tanto le tenemos que decir await() esto es corrutinas en general
            Firebase.auth.signInWithEmailAndPassword(email, password).await()
            //Si no hay error entonces devolvemos un success
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun signup(email: String, password: String): Result<Unit> {
        return try {
            //Firebase no soporta corrutinas por lo tanto le tenemos que decir await() esto es corrutinas en general
            Firebase.auth.createUserWithEmailAndPassword(email, password).await()
            //Si no hay error entonces devolvemos un success
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}