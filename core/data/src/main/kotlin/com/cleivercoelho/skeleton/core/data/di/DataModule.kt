package com.cleivercoelho.skeleton.core.data.di

import com.cleivercoelho.skeleton.core.data.repository.UserRepositoryImpl
import com.cleivercoelho.skeleton.core.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun bindUserRepository(
        impl: UserRepositoryImpl
    ): UserRepository
}
