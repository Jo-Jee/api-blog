package kr.co.jojee.blog.api.entity

import kr.co.jojee.blog.api.dto.TagResponse
import org.hibernate.annotations.Formula
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany
import kotlin.collections.HashSet

@Entity
class Tag(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(unique = true, nullable = false)
    val name: String,
    @OneToMany(mappedBy = "tag")
    var posts: MutableSet<PostTag> = HashSet(),
    @Formula("(SELECT COUNT(*) FROM post_tag pt WHERE pt.tag_id = id)")
    var count: Int = 0
) {
    fun toDto(): TagResponse {
        return TagResponse(
            id = id,
            name = name,
            count = count
        )
    }
}