package com.example.user.api.controller

import com.example.user.api.dto.RegisterRequestDto
import com.example.user.api.dto.LoginRequestDto
import com.example.user.api.dto.LoginResponseDto
import com.example.user.api.dto.UserResponseDto
import com.example.user.api.service.AuthService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@Tag(
    name = "Auth",
    description = "인증 API"
)
@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    val authService: AuthService
) {
    @PostMapping("/login")
    fun loginWithIdPassword(@RequestBody loginRequestDto: LoginRequestDto): LoginResponseDto {
        return authService.login(loginRequestDto)
    }

    @PostMapping("/register")
    fun register(
        @Valid @RequestBody newUser: RegisterRequestDto
    ): UserResponseDto {
        return authService.register(newUser).toDto()
    }
}