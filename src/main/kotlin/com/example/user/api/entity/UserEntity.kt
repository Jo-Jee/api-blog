package com.example.user.api.entity

import com.example.user.api.dto.AddUserRequestDto
import com.example.user.api.dto.UserResponseDto
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val email: String,
    val password: String
) {
    fun toDto(): UserResponseDto {
        return UserResponseDto(
            id = id,
            email = email,
            password = password
        )
    }
}
