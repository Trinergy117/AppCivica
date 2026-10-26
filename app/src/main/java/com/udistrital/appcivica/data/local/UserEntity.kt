package com.udistrital.appcivica.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val email: String,
    val passwordHash: String,
    val name: String,
    val role: String = "CIUDADANO",
    val isVulnerable: Boolean = false
)
