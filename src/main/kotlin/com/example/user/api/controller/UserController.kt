package com.example.user.api.controller

import com.example.user.api.dto.AddUserRequestDto
import com.example.user.api.dto.UserResponseDto
import com.example.user.api.service.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/users")
class UserController (
    val userService: UserService
) {
    @GetMapping("/{id}")
    fun getUser(@PathVariable id: Long): UserResponseDto {
        return userService.findUserById(id)
    }

    @PostMapping
    fun addUser(@RequestBody newUser: AddUserRequestDto): UserResponseDto {
        return userService.addUser(newUser)
    }
}
