package kr.co.jojee.blog.api.dto

data class LoginResponse(
    val accessToken: String,
    val refreshToken: String
)
