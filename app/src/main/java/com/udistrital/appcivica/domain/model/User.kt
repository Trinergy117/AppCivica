package com.udistrital.appcivica.domain.model

data class User(
    val id: Long = 0,
    val email: String,
    val name: String,
    val role: String,
    val isVulnerable: Boolean = false
)
