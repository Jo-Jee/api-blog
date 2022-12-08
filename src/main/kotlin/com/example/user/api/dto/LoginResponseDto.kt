package com.example.user.api.dto

data class LoginResponseDto(
    val accessToken: String,
    val refreshToken: String
)
