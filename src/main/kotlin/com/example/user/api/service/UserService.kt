package com.example.user.api.service

import com.example.user.api.dto.AddUserRequestDto
import com.example.user.api.dto.UserResponseDto
import com.example.user.api.entity.User
import com.example.user.api.repository.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class UserService(
    val userRepository: UserRepository,
    val passwordEncoder: PasswordEncoder
) {
    fun findUserById(id: Long): UserResponseDto {
        return userRepository
            .findById(id)
            .orElseThrow{throw ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 uid입니다.")}
            .toDto();
    }

    fun addUser(newUser: AddUserRequestDto): UserResponseDto {
        val user = User(
            email = newUser.email,
            encodedPassword = passwordEncoder.encode(newUser.password)
        )
        return userRepository.save(user).toDto()
    }

    fun findUserByEmail(email: String): UserResponseDto {
        return userRepository.findByEmail(email)?.toDto() ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 email입니다.")
    }
}
