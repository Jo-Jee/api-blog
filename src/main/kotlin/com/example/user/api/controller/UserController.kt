package com.example.user.api.controller

import com.example.user.api.dto.RegisterResponseDto
import com.example.user.api.service.UserService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(
    name = "User",
    description = "사용자 API"
)
@RestController
@RequestMapping("/api/v1/users")
class UserController (
    val userService: UserService
) {
    @GetMapping("/{id}")
    fun getUser(@PathVariable id: Long): RegisterResponseDto {
        return userService.findUserById(id).toDto()
    }
}
