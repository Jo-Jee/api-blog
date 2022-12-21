package kr.co.jojee.blog.api.service

import kr.co.jojee.blog.api.entity.User
import kr.co.jojee.blog.api.repository.UserRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class UserService(
    val userRepository: UserRepository
) {
    fun findById(id: Long): User {
        return userRepository
            .findById(id)
            .orElseThrow{throw ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 uid입니다.")}
    }

    fun add(email: String, encodedPassword: String): User {
        val user = User(
            email = email,
            encodedPassword = encodedPassword
        )
        return userRepository.save(user)
    }

    fun findByEmail(email: String): User {
        return userRepository.findByEmail(email) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 email입니다.")
    }

    fun findAll(pageable: Pageable): Page<User> {
        return userRepository.findAll(pageable)
    }
}
