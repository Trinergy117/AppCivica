package com.udistrital.appcivica.data.repository

import com.udistrital.appcivica.data.local.UserDao
import com.udistrital.appcivica.data.mapper.toDomain
import com.udistrital.appcivica.data.mapper.toEntity
import com.udistrital.appcivica.domain.model.User
import com.udistrital.appcivica.domain.repository.UserRepository

class UserRepositoryImpl(
    private val userDao: UserDao
) : UserRepository {

    override suspend fun getUserByEmail(email: String): User? {
        return userDao.getUserByEmail(email)?.toDomain()
    }

    override suspend fun authenticate(email: String, password: String): User? {
        return userDao.authenticate(email, password)?.toDomain()
    }

    override suspend fun insertUser(user: User, password: String): Long {
        return userDao.insertUser(user.toEntity(passwordHash = password))
    }

    override suspend fun getAllUsers(): List<User> {
        return userDao.getAllUsers().map { it.toDomain() }
    }
}
