package com.cleivercoelho.skeleton.core.data.repository

import com.cleivercoelho.skeleton.core.common.result.Resource
import com.cleivercoelho.skeleton.core.data.mapper.toDomain
import com.cleivercoelho.skeleton.core.data.mapper.toDomainList
import com.cleivercoelho.skeleton.core.data.mapper.toEntity
import com.cleivercoelho.skeleton.core.data.mapper.toEntityList
import com.cleivercoelho.skeleton.core.database.dao.UserDao
import com.cleivercoelho.skeleton.core.datastore.AppDataStore
import com.cleivercoelho.skeleton.core.domain.repository.UserRepository
import com.cleivercoelho.skeleton.core.model.User
import com.cleivercoelho.skeleton.core.network.api.UserApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiService: UserApiService,
    private val userDao: UserDao,
    private val dataStore: AppDataStore
) : UserRepository {

    override fun getUsers(): Flow<Resource<List<User>>> = flow {
        emit(Resource.Loading)

        try {
            userDao.getAllUsers().collect { cachedUsers ->
                if (cachedUsers.isNotEmpty()) {
                    emit(Resource.Success(cachedUsers.toDomainList()))
                }
            }
        } catch (e: Exception) {
            emit(Resource.Error("Failed to load users: ${e.localizedMessage}", e))
        }
    }.flowOn(Dispatchers.IO)

    override fun getUserById(id: Int): Flow<Resource<User>> =
        userDao.getUserById(id).map { entity ->
            entity?.let { Resource.Success(it.toDomain()) }
                ?: Resource.Error("User not found")
        }.flowOn(Dispatchers.IO)

    override suspend fun refreshUsers(): Resource<Unit> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getUsers()
            if (response.isSuccessful) {
                response.body()?.let { users ->
                    userDao.deleteAllUsers()
                    userDao.insertUsers(users.toEntityList())
                    dataStore.updateLastSyncTime(System.currentTimeMillis())
                    Resource.Success(Unit)
                } ?: Resource.Error("Empty response body")
            } else {
                Resource.Error("API error: ${response.code()}")
            }
        } catch (e: Exception) {
            Resource.Error("Network error: ${e.localizedMessage}", e)
        }
    }

    override suspend fun saveUser(user: User): Resource<Unit> = withContext(Dispatchers.IO) {
        try {
            userDao.insertUser(user.toEntity())
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error("Failed to save user: ${e.localizedMessage}", e)
        }
    }

    override suspend fun deleteUser(id: Int): Resource<Unit> = withContext(Dispatchers.IO) {
        try {
            userDao.deleteUser(id)
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error("Failed to delete user: ${e.localizedMessage}", e)
        }
    }
}
