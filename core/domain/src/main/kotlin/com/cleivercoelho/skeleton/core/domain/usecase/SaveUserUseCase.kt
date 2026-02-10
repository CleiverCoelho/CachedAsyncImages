package com.cleivercoelho.skeleton.core.domain.usecase

import com.cleivercoelho.skeleton.core.common.result.Resource
import com.cleivercoelho.skeleton.core.domain.repository.UserRepository
import com.cleivercoelho.skeleton.core.model.User
import javax.inject.Inject

class SaveUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(user: User): Resource<Unit> = repository.saveUser(user)
}
