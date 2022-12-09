package com.example.user.api.service

import com.example.user.api.auth.JwtUtil
import com.example.user.api.dto.RegisterRequestDto
import com.example.user.api.dto.LoginRequestDto
import com.example.user.api.dto.LoginResponseDto
import com.example.user.api.entity.User
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class AuthService(
    val passwordEncoder: PasswordEncoder,
    val jwtUtil: JwtUtil,
    val userService: UserService
) {
    fun login(
        loginRequestDto: LoginRequestDto
    ): LoginResponseDto {
        val user = userService.findUserByEmail(loginRequestDto.email)

        if (!passwordEncoder.matches(loginRequestDto.password, user.encodedPassword))
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 틀렸습니다.")

        return jwtUtil.createToken(user)
    }

    fun register(newUser: RegisterRequestDto): User {
        return userService.addUser(newUser.email, passwordEncoder.encode(newUser.password))
    }
}