package kr.co.jojee.blog.api.entity

import kr.co.jojee.blog.api.dto.PostListResponse
import kr.co.jojee.blog.api.dto.PostResponse
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.OrderBy

@Entity
class Post(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    var title: String,

    @Column(nullable = false)
    var summary: String,

    @ManyToOne
    var topic: Topic,

    var published: Boolean = false,

    @OneToMany(mappedBy = "post", cascade = [CascadeType.ALL])
    @OrderBy("id")
    var tags: MutableSet<PostTag> = HashSet(),

    @Column(columnDefinition = "TEXT")
    var body: String,

    @Column(nullable = false)
    var publishedAt: LocalDateTime? = null

): Timestamped() {
    fun toDto(): PostResponse {
        return PostResponse(
            id = id,
            title = title,
            tags = tags.map { it.tag.name },
            summary = summary,
            topicId = topic.id!!,
            published = published,
            body = body,
            publishedAt = publishedAt.toString()
        )
    }

    fun toListDto(): PostListResponse {
        return PostListResponse(
            id = id,
            title = title,
            tags = tags.map {it.tag.name},
            summary = summary,
            published = published,
            createdAt = createdAt.toString(),
            updatedAt = updatedAt.toString(),
            publishedAt = publishedAt.toString()
        )
    }
}


