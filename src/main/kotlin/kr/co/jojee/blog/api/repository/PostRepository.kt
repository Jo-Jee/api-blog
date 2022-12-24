package kr.co.jojee.blog.api.repository

import kr.co.jojee.blog.api.entity.Post
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PostRepository: JpaRepository<Post, Long?> {
}