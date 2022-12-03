package com.example.user.api.entity

import com.example.user.api.dto.UserDto
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
    fun toDto(): UserDto {
        return UserDto(
            id = id,
            email = email,
            password = password
        )
    }
}
