package kr.co.jojee.blog.api.entity

import kr.co.jojee.blog.api.dto.TopicResponse
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Topic(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false, unique = true)
    val name: String
) {
    fun toDto(): TopicResponse {
        return TopicResponse(
            id = id,
            name = name
        )
    }
}