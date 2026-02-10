package com.cleivercoelho.skeleton.core.data.mapper

import com.cleivercoelho.skeleton.core.database.entity.UserEntity
import com.cleivercoelho.skeleton.core.model.User
import com.cleivercoelho.skeleton.core.network.dto.UserDto

fun UserDto.toEntity(): UserEntity = UserEntity(
    id = id,
    name = name,
    email = email,
    phone = phone
)

fun UserEntity.toDomain(): User = User(
    id = id,
    name = name,
    email = email,
    phone = phone,
    avatarUrl = generateAvatarUrl(id)
)

fun User.toEntity(): UserEntity = UserEntity(
    id = id,
    name = name,
    email = email,
    phone = phone
)

fun List<UserDto>.toEntityList(): List<UserEntity> = map { it.toEntity() }
fun List<UserEntity>.toDomainList(): List<User> = map { it.toDomain() }

private fun generateAvatarUrl(id: Int): String = "https://i.pravatar.cc/128?u=$id"
