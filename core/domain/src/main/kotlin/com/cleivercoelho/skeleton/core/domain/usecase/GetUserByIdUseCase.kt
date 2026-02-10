package com.cleivercoelho.skeleton.core.domain.usecase

import com.cleivercoelho.skeleton.core.common.result.Resource
import com.cleivercoelho.skeleton.core.domain.repository.UserRepository
import com.cleivercoelho.skeleton.core.model.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserByIdUseCase @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke(id: Int): Flow<Resource<User>> = repository.getUserById(id)
}
