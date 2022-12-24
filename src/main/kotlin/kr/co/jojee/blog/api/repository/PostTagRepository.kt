package kr.co.jojee.blog.api.repository

import kr.co.jojee.blog.api.entity.Post
import kr.co.jojee.blog.api.entity.PostTag
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PostTagRepository: JpaRepository<PostTag, Long?> {
    fun deleteAllByPost(post: Post): List<PostTag>
}