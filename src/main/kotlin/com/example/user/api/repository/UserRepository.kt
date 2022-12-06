package com.example.user.api.repository

import com.example.user.api.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: JpaRepository<UserEntity, Long?> {
    fun findByEmail(email: String): UserEntity?
}