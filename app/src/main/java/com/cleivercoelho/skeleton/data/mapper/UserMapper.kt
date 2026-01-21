package com.cleivercoelho.skeleton.data.mapper

import com.cleivercoelho.skeleton.data.local.database.entity.UserEntity
import com.cleivercoelho.skeleton.data.remote.dto.UserDto
import com.cleivercoelho.skeleton.domain.model.User

fun UserDto.toEntity(): UserEntity = UserEntity(
    id = id,
    name = name,
    email = email,
    phone = phone,
)

fun UserEntity.toDomain(): User = User(
    id = id,
    name = name,
    email = email,
    phone = phone,
    avatarUrl = generateAvatarUrl(id, name)
)

fun User.toEntity(): UserEntity = UserEntity(
    id = id,
    name = name,
    email = email,
    phone = phone,
)

fun List<UserDto>.toEntityList(): List<UserEntity> = map { it.toEntity() }
fun List<UserEntity>.toDomainList(): List<User> = map { it.toDomain() }

// Generate avatar using ui-avatars.com or pravatar.cc
private fun generateAvatarUrl(id: Int, name: String): String {
// Option 1: UI Avatars (initials-based)
//    val encodedName = URLEncoder.encode(name, "UTF-8")
//    return "https://ui-avatars.com/api/?name=$encodedName&background=random&size=128"

// Option 2: Random avatar based on id
 return "https://i.pravatar.cc/128?u=$id"

// Option 3: DiceBear avatars
// return "https://api.dicebear.com/7.x/avataaars/png?seed=$id"
}