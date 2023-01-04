package kr.co.jojee.blog.api.controller

import kr.co.jojee.blog.api.dto.UserResponse
import kr.co.jojee.blog.api.service.UserService
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import kr.co.jojee.blog.api.entity.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

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
    fun getUserById(@PathVariable id: Long): UserResponse {
        return userService.findById(id).toDto()
    }

    @GetMapping("/")
    fun getAllUsers(@RequestParam page: Int, @RequestParam size: Int): Page<UserResponse> {
        val pageRequest = PageRequest.of(page, size, Sort.by("id").descending())
        return userService.findAll(pageRequest).map { it.toDto() }
    }

    @GetMapping("/my-profile")
    fun getMyProfile(authentication: Authentication): UserResponse {
        val user: User = authentication.principal as User

        return user.toDto()
    }
}
