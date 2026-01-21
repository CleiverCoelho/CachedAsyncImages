package com.cleivercoelho.skeleton.di

import android.content.Context
import androidx.room.Room
import com.cleivercoelho.skeleton.data.local.database.AppDatabase
import com.cleivercoelho.skeleton.data.local.database.dao.UserDao
import com.cleivercoelho.skeleton.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        Constants.DATABASE_NAME
    ).fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideUserDao(database: AppDatabase): UserDao = database.userDao()
}