package com.alex.habitsapp.feature.home.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.alex.habitsapp.feature.home.data.local.entity.HabitEntity
import com.alex.habitsapp.feature.home.data.local.entity.HabitSyncEntity
import com.alex.habitsapp.feature.home.data.typeconverter.HomeTypeConverter

@Database(entities = [HabitEntity::class, HabitSyncEntity::class], version = 1)
@TypeConverters(HomeTypeConverter::class)
abstract class HomeDatabase: RoomDatabase() {
    abstract fun dao(): HomeDao
}