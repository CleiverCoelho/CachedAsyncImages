package com.cleivercoelho.skeleton.core.domain.repository

import com.cleivercoelho.skeleton.core.common.result.Resource
import com.cleivercoelho.skeleton.core.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUsers(): Flow<Resource<List<User>>>
    fun getUserById(id: Int): Flow<Resource<User>>
    suspend fun refreshUsers(): Resource<Unit>
    suspend fun saveUser(user: User): Resource<Unit>
    suspend fun deleteUser(id: Int): Resource<Unit>
}
