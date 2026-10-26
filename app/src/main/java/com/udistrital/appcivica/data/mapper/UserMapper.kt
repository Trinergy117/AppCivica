package com.udistrital.appcivica.data.mapper

import com.udistrital.appcivica.data.local.UserEntity
import com.udistrital.appcivica.domain.model.User

fun UserEntity.toDomain(): User {
    return User(
        id = id,
        email = email,
        name = name,
        role = role,
        isVulnerable = isVulnerable
    )
}

fun User.toEntity(passwordHash: String): UserEntity {
    return UserEntity(
        id = id,
        email = email,
        passwordHash = passwordHash,
        name = name,
        role = role,
        isVulnerable = isVulnerable
    )
}
