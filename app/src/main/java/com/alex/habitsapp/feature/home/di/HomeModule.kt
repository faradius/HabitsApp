package com.alex.habitsapp.feature.home.di

import com.alex.habitsapp.feature.home.data.repository.HomeRepositoryImpl
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
    fun provideHomeRepository(): HomeRepository {
        return HomeRepositoryImpl()
    }
}