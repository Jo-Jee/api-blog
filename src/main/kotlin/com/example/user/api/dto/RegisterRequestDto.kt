package com.example.user.api.dto

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

data class RegisterRequestDto(
    @field:NotBlank
    @field:Email
    val email: String,
    @field:NotBlank
    val password: String
)
