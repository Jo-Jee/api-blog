package com.example.user.api.controller

import com.example.user.api.dto.UserResponseDto
import com.example.user.api.service.UserService
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(
    name = "User",
    description = "사용자 API"
)
@SecurityRequirement(name="bearerAuth")
@RestController
@RequestMapping("/api/v1/users")
class UserController (
    val userService: UserService
) {
    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long): UserResponseDto {
        return userService.findById(id).toDto()
    }

    @GetMapping("/")
    fun getAllUsers(pageable: Pageable): Page<UserResponseDto> {
        return userService.findAll(pageable).map { it.toDto()  }
    }
}
