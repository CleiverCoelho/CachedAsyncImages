package com.cleivercoelho.skeleton.domain.usecase

import com.cleivercoelho.skeleton.domain.model.User
import com.cleivercoelho.skeleton.domain.repository.UserRepository
import com.cleivercoelho.skeleton.util.Resource
import javax.inject.Inject

class SaveUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(user: User): Resource<Unit> = repository.saveUser(user)
}