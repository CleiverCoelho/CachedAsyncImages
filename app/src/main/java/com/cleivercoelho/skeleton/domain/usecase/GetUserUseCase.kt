package com.cleivercoelho.skeleton.domain.usecase

import com.cleivercoelho.skeleton.domain.model.User
import com.cleivercoelho.skeleton.domain.repository.UserRepository
import com.cleivercoelho.skeleton.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke(): Flow<Resource<List<User>>> = repository.getUsers()
}