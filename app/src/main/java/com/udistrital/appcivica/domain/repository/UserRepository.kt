package com.udistrital.appcivica.domain.repository

import com.udistrital.appcivica.domain.model.User

interface UserRepository {
    suspend fun getUserByEmail(email: String): User?
    suspend fun authenticate(email: String, password: String): User?
    suspend fun insertUser(user: User, password: String): Long
    suspend fun getAllUsers(): List<User>
}
