package com.alex.habitsapp.feature.authentication.di

import com.alex.habitsapp.feature.authentication.data.matcher.EmailMatcherImpl
import com.alex.habitsapp.feature.authentication.data.repository.AuthenticationRepositoryImpl
import com.alex.habitsapp.feature.authentication.domain.matcher.EmailMatcher
import com.alex.habitsapp.feature.authentication.domain.repository.AuthenticationRepository
import com.alex.habitsapp.feature.authentication.domain.usecase.GetUserIdUseCase
import com.alex.habitsapp.feature.authentication.domain.usecase.LoginUseCases
import com.alex.habitsapp.feature.authentication.domain.usecase.LoginWithEmailUseCase
import com.alex.habitsapp.feature.authentication.domain.usecase.SignupUseCases
import com.alex.habitsapp.feature.authentication.domain.usecase.SignupWithEmailUseCase
import com.alex.habitsapp.feature.authentication.domain.usecase.ValidateEmailUseCase
import com.alex.habitsapp.feature.authentication.domain.usecase.ValidatePasswordUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthenticationModule {
    @Provides
    @Singleton
    fun provideAuthenticationRepository(): AuthenticationRepository {
        return AuthenticationRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideEmailMatcher(): EmailMatcher {
        return EmailMatcherImpl()
    }

    @Provides
    @Singleton
    fun provideLoginUseCases(repository: AuthenticationRepository,emailMatcher: EmailMatcher): LoginUseCases {
        return LoginUseCases(
            loginWithEmailUseCase = LoginWithEmailUseCase(repository),
            validateEmailUseCase = ValidateEmailUseCase(emailMatcher),
            validatePasswordUseCase = ValidatePasswordUseCase()
        )
    }

    @Provides
    @Singleton
    fun provideSignupUseCases(repository: AuthenticationRepository,emailMatcher: EmailMatcher): SignupUseCases {
        return SignupUseCases(
            signupWithEmailUseCase = SignupWithEmailUseCase(repository),
            validateEmailUseCase = ValidateEmailUseCase(emailMatcher),
            validatePasswordUseCase = ValidatePasswordUseCase()
        )
    }

    @Provides
    @Singleton
    fun provideGetUserIdUseCase(repository: AuthenticationRepository): GetUserIdUseCase {
        return GetUserIdUseCase(repository)
    }
}