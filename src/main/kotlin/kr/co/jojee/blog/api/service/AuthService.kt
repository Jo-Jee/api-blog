package kr.co.jojee.blog.api.service

import io.jsonwebtoken.JwtException
import kr.co.jojee.blog.api.auth.JwtUtil
import kr.co.jojee.blog.api.dto.RegisterRequest
import kr.co.jojee.blog.api.dto.LoginRequest
import kr.co.jojee.blog.api.dto.TokenResponse
import kr.co.jojee.blog.api.dto.ReissueTokenRequest
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
        loginRequest: LoginRequest
    ): TokenResponse {
        val user = userService.findByEmail(loginRequest.email)

        if (!passwordEncoder.matches(loginRequest.password, user.encodedPassword))
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 틀렸습니다.")

        return jwtUtil.createToken(user)
    }

    fun register(newUser: RegisterRequest): User {
        return userService.add(newUser.email, passwordEncoder.encode(newUser.password))
    }

    fun reissue(reissueTokenRequest: ReissueTokenRequest): TokenResponse {
        try {
            val uid = jwtUtil.validateRefreshToken(reissueTokenRequest.refreshToken)
            return jwtUtil.createToken(userService.findById(uid))
        } catch (jwtException: JwtException) {
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "토큰 인증에 실패했습니다.")
        }
    }
}