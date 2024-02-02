package com.alex.habitsapp.feature.home.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alex.habitsapp.feature.home.data.local.entity.HabitEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HomeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabit(habitEntity: HabitEntity)

    @Query("SELECT * FROM HabitEntity WHERE id = :id")
    suspend fun getHabitById(id: String): HabitEntity

    @Query("SELECT * FROM HabitEntity WHERE startDate <= :date ORDER BY id ASC")
    fun getAllHabitsForSelectedDate(date: Long): Flow<List<HabitEntity>>
}