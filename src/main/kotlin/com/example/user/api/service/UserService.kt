package com.example.user.api.service

import com.example.user.api.dto.UserDto
import com.example.user.api.entity.UserEntity
import com.example.user.api.repository.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class UserService(
    val userRepository: UserRepository
) {
    fun findUserById(id: Long): UserDto {
        return userRepository
            .findById(id)
            .orElseThrow{throw ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 uid입니다.")}
            .toDto();
    }

    fun newUser(user: UserDto): UserDto {
        return userRepository.save(user.toEntity()).toDto()
    }
}