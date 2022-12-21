package kr.co.jojee.blog.api.service

import kr.co.jojee.blog.api.auth.JwtUtil
import kr.co.jojee.blog.api.dto.RegisterRequestDto
import kr.co.jojee.blog.api.dto.LoginRequestDto
import kr.co.jojee.blog.api.dto.LoginResponseDto
import kr.co.jojee.blog.api.entity.User
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
        val user = userService.findByEmail(loginRequestDto.email)

        if (!passwordEncoder.matches(loginRequestDto.password, user.encodedPassword))
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 틀렸습니다.")

        return jwtUtil.createToken(user)
    }

    fun register(newUser: RegisterRequestDto): User {
        return userService.add(newUser.email, passwordEncoder.encode(newUser.password))
    }
}