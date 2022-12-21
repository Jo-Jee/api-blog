package kr.co.jojee.blog.api.entity

import kr.co.jojee.blog.api.dto.PostResponseDto
import java.util.TreeSet
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.OneToMany

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
    @Column()
    var published: Boolean = false,
    @OneToMany(mappedBy = "post", cascade = [CascadeType.ALL])
    val tags: MutableSet<PostTag> = HashSet()

): Timestamped() {
    fun toDto(): PostResponseDto {
        return PostResponseDto(
            id = id,
            title = title
        )
    }
}


