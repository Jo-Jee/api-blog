package kr.co.jojee.blog.api.controller

import kr.co.jojee.blog.api.dto.RegisterRequest
import kr.co.jojee.blog.api.dto.LoginRequest
import kr.co.jojee.blog.api.dto.LoginResponse
import kr.co.jojee.blog.api.dto.UserResponse
import kr.co.jojee.blog.api.service.AuthService
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
    fun loginWithIdPassword(@RequestBody loginRequest: LoginRequest): LoginResponse {
        return authService.login(loginRequest)
    }

    @PostMapping("/register")
    fun register(
        @Valid @RequestBody newUser: RegisterRequest
    ): UserResponse {
        return authService.register(newUser).toDto()
    }
}