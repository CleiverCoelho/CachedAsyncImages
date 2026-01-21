package com.cleivercoelho.skeleton.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cleivercoelho.skeleton.data.local.database.dao.UserDao
import com.cleivercoelho.skeleton.data.local.database.entity.UserEntity

@Database(
    entities = [UserEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
