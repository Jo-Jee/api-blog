package com.example.user.api.service

import com.example.user.api.entity.User
import com.example.user.api.repository.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class UserService(
    val userRepository: UserRepository
) {
    fun findUserById(id: Long): User {
        return userRepository
            .findById(id)
            .orElseThrow{throw ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 uid입니다.")}
    }

    fun addUser(email: String, encodedPassword: String): User {
        val user = User(
            email = email,
            encodedPassword = encodedPassword
        )
        return userRepository.save(user)
    }

    fun findUserByEmail(email: String): User {
        return userRepository.findByEmail(email) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 email입니다.")
    }
}
