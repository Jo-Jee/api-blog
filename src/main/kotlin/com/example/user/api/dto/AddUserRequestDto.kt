package com.example.user.api.dto

import com.example.user.api.entity.User

data class AddUserRequestDto(
    val email: String,
    val password: String
)
