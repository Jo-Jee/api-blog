package kr.co.jojee.blog.api.repository

import kr.co.jojee.blog.api.entity.Post
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface PostRepository: JpaRepository<Post, Long?> {
    @Query("SELECT p.id FROM Post p")
    fun findAllId(): List<Long>
    @Query("SELECT p FROM Post p INNER JOIN p.tags pt INNER JOIN pt.tag t WHERE t.name = :tag AND p.published = TRUE AND p.publishedAt <= :now")
    fun findPublishedPostsByTag(@Param("tag") tag: String, @Param("now") now: LocalDateTime, pageable: Pageable): Page<Post>
    fun findByPublishedTrueAndPublishedAtLessThanEqual(now: LocalDateTime, pageable: Pageable): Page<Post>
    @Query("SELECT p.viewCount FROM Post p WHERE p.id = :id")
    fun findViewCountById(@Param("id") id: Long): Long
}