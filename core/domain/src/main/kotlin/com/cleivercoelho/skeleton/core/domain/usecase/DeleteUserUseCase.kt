package com.cleivercoelho.skeleton.core.domain.usecase

import com.cleivercoelho.skeleton.core.common.result.Resource
import com.cleivercoelho.skeleton.core.domain.repository.UserRepository
import javax.inject.Inject

class DeleteUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(id: Int): Resource<Unit> = repository.deleteUser(id)
}
