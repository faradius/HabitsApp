package com.alex.habitsapp.feature.home.di

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Room
import com.alex.habitsapp.feature.home.data.local.HomeDao
import com.alex.habitsapp.feature.home.data.local.HomeDatabase
import com.alex.habitsapp.feature.home.data.remote.HomeApi
import com.alex.habitsapp.feature.home.data.repository.HomeRepositoryImpl
import com.alex.habitsapp.feature.home.data.typeconverter.HomeTypeConverter
import com.alex.habitsapp.feature.home.domain.detail.usecase.DetailUseCases
import com.alex.habitsapp.feature.home.domain.detail.usecase.GetHabitByIdUseCase
import com.alex.habitsapp.feature.home.domain.detail.usecase.InsertHabitUseCase
import com.alex.habitsapp.feature.home.domain.home.usecase.CompleteHabitUseCase
import com.alex.habitsapp.feature.home.domain.home.usecase.GetHabitForDateUseCase
import com.alex.habitsapp.feature.home.domain.home.usecase.HomeUseCases
import com.alex.habitsapp.feature.home.domain.repository.HomeRepository
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
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

    @Singleton
    @Provides
    fun provideHabitDao(@ApplicationContext context: Context, moshi: Moshi): HomeDao {
        return Room.databaseBuilder(
            context,
            HomeDatabase::class.java,
            "habits_db"
        ).addTypeConverter(HomeTypeConverter(moshi)).build().dao()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        ).build()
    }

    @Singleton
    @Provides
    fun provideHomeApi(client: OkHttpClient): HomeApi {
        return Retrofit.Builder().baseUrl(HomeApi.BASE_URL).client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .build().create(HomeApi::class.java)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Singleton
    @Provides
    fun provideHomeRepository(dao: HomeDao, api: HomeApi): HomeRepository {
        return HomeRepositoryImpl(dao, api)
    }

    @Singleton
    @Provides
    fun provideMoshi(): Moshi{
        return Moshi.Builder().build()
    }
}