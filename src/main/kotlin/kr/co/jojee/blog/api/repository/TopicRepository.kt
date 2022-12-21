package kr.co.jojee.blog.api.repository

import kr.co.jojee.blog.api.entity.Topic
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TopicRepository: JpaRepository<Topic, Long?> {
}