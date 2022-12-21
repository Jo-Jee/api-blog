package kr.co.jojee.blog.api.service

import kr.co.jojee.blog.api.entity.*
import kr.co.jojee.blog.api.repository.PostRepository
import kr.co.jojee.blog.api.repository.TagRepository
import kr.co.jojee.blog.api.repository.TopicRepository
import kr.co.jojee.blog.api.repository.UserRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.util.*
import javax.transaction.Transactional

@Service
class BlogService(
    val postRepository: PostRepository,
    val topicRepository: TopicRepository,
    val tagRepository: TagRepository
) {
//    fun findById(id: Long): User {
//        return userRepository
//            .findById(id)
//            .orElseThrow{throw ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 uid입니다.")}
//    }
//
    @Transactional
    fun addPost(title: String, summary: String, topicId: Long, published: Boolean, tags: List<String>): Post {
        val post = Post(
            title = title,
            summary = summary,
            topic = findTopicById(topicId),
            published = published,
        )

        for (tagName in tags) {
            val tag = findTagByName(tagName) ?: Tag(name = tagName)
            val postTag = PostTag(post = post, tag = tag)

            tag.posts.add(postTag)
            post.tags.add(postTag)

            tagRepository.save(tag)
        }

        return postRepository.save(post)
    }

    fun addTopic(name: String): Topic {
        val topic = Topic(
            name = name
        )
        return topicRepository.save(topic)
    }
//
//    fun findByEmail(email: String): User {
//        return userRepository.findByEmail(email) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 email입니다.")
//    }

    fun findTopicById(id: Long): Topic {
        return topicRepository.findById(id).orElseThrow{throw ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 email입니다.")}
    }

    fun findAllPosts(pageable: Pageable): Page<Post> {
        return postRepository.findAll(pageable)
    }

    fun findTagByName(name: String): Tag? {
        return tagRepository.findByName(name)
    }
}
