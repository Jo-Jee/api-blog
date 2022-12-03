package com.example.user.api.dto

import com.example.user.api.entity.UserEntity

data class AddUserRequestDto(
    val email: String,
    val password: String
) {
    fun toEntity(): UserEntity {
        return UserEntity(
            email = email,
            password = password
        )
    }
}
