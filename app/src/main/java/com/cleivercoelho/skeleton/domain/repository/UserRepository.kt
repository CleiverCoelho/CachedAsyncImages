package com.cleivercoelho.skeleton.domain.repository

import com.cleivercoelho.skeleton.domain.model.User
import com.cleivercoelho.skeleton.util.Resource
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUsers(): Flow<Resource<List<User>>>
    fun getUserById(id: Int): Flow<Resource<User>>
    suspend fun refreshUsers(): Resource<Unit>
    suspend fun saveUser(user: User): Resource<Unit>
    suspend fun deleteUser(id: Int): Resource<Unit>
}