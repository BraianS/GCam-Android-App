package dev.braian.gcamxmlhub.core

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.braian.gcamxmlhub.data.repository.AuthRepository
import dev.braian.gcamxmlhub.data.repository.impl.LoginService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModule {

    @Binds
    @Singleton
    abstract fun bindLoginRepository(loginService: LoginService): AuthRepository



}