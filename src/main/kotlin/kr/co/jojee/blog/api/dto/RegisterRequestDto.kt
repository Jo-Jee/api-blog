package kr.co.jojee.blog.api.dto

import io.swagger.v3.oas.annotations.media.Schema
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

data class RegisterRequestDto(
    @field:Schema(
        example = "example@example.com"
    )
    @field:NotBlank(message = "이메일이 비어있습니다.")
    @field:Email(message = "이메일 형식이 유효하지 않습니다.")
    val email: String,
    @field:NotBlank(message = "비밀번호가 비어있습니다.")
    val password: String
)
