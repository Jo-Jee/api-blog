package kr.co.jojee.blog.api.dto

data class TokenResponse(
    val accessToken: String,
    val refreshToken: String
)
