package com.alex.habitsapp.feature.home.di

import android.os.Build
import androidx.annotation.RequiresApi
import com.alex.habitsapp.feature.home.data.repository.HomeRepositoryImpl
import com.alex.habitsapp.feature.home.domain.detail.usecase.DetailUseCases
import com.alex.habitsapp.feature.home.domain.detail.usecase.GetHabitByIdUseCase
import com.alex.habitsapp.feature.home.domain.detail.usecase.InsertHabitUseCase
import com.alex.habitsapp.feature.home.domain.home.usecase.CompleteHabitUseCase
import com.alex.habitsapp.feature.home.domain.home.usecase.GetHabitForDateUseCase
import com.alex.habitsapp.feature.home.domain.home.usecase.HomeUseCases
import com.alex.habitsapp.feature.home.domain.repository.HomeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeModule {
    @Singleton
    @Provides
    fun provideHomeUseCases(repository: HomeRepository) = HomeUseCases(
        getHabitForDateUseCase = GetHabitForDateUseCase(repository),
        completeHabitUseCase = CompleteHabitUseCase(repository)
    )

    @Singleton
    @Provides
    fun provideDetailUseCases(repository: HomeRepository) = DetailUseCases(
        getHabitByIdUseCase = GetHabitByIdUseCase(repository),
        insertHabitUseCase = InsertHabitUseCase(repository)
    )

    @RequiresApi(Build.VERSION_CODES.O)
    @Singleton
    @Provides
    fun provideHomeRepository(): HomeRepository {
        return HomeRepositoryImpl()
    }
}