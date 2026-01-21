package com.cleivercoelho.skeleton.di

import android.content.Context
import com.cleivercoelho.skeleton.data.local.datastore.AppDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDataStore(
        @ApplicationContext context: Context
    ): AppDataStore = AppDataStore(context)
}