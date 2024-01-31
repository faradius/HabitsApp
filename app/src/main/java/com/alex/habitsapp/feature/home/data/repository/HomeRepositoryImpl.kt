package com.alex.habitsapp.feature.home.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.alex.habitsapp.feature.home.data.extension.toStartOfDateTimestamp
import com.alex.habitsapp.feature.home.data.local.HomeDao
import com.alex.habitsapp.feature.home.data.mapper.toDomain
import com.alex.habitsapp.feature.home.data.mapper.toEntity
import com.alex.habitsapp.feature.home.domain.model.Habit
import com.alex.habitsapp.feature.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.ZonedDateTime

@RequiresApi(Build.VERSION_CODES.O)
class HomeRepositoryImpl(
    private val dao: HomeDao
) : HomeRepository {




    override fun getAllHabitsForSelectedDate(date: ZonedDateTime): Flow<List<Habit>> {
        return dao.getAllHabitsForSelectedDate(date.toStartOfDateTimestamp()).map { it.map { it.toDomain() } }
    }


    override suspend fun insertHabit(habit: Habit) {
        dao.insertHabit(habit.toEntity())
    }


    override fun getHabitById(id: String): Habit {
        return dao.getHabitById(id).toDomain()
    }
}