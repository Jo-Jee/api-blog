package kr.co.jojee.blog.api.dto

data class LoginResponseDto(
    val accessToken: String,
    val refreshToken: String
)
