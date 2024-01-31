package com.alex.habitsapp.feature.home.data.remote

import com.alex.habitsapp.feature.home.data.remote.dto.HabitDto
import com.alex.habitsapp.feature.home.data.remote.dto.HabitResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH

interface HomeApi {
    companion object{
        const val BASE_URL = "https://habitsapp-d6038-default-rtdb.firebaseio.com/"
    }

    @GET("habits.json")
    suspend fun getAllHabits(): HabitResponse

    //El @UPDATE es la actualización de un objeto completo y el @PATCH es la actualización
    //de algunos elementos del objeto (si solo el nombre del habito, fecha, etc.)
    @PATCH("habits.json") //Esto lo utilizaremos tanto para insertar como para actualizar
    suspend fun insertHabit(@Body habit: HabitResponse)
}