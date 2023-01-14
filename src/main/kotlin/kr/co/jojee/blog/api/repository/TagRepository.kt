package kr.co.jojee.blog.api.repository

import kr.co.jojee.blog.api.entity.Tag
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface TagRepository: JpaRepository<Tag, Long?> {
    fun findByName(name: String): Tag?
    @Query("SELECT t.name FROM Tag t")
    fun getAllTagNames(): List<String>
}