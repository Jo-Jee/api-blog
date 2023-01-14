package kr.co.jojee.blog.api.service

import kr.co.jojee.blog.api.dto.PostRequest
import kr.co.jojee.blog.api.dto.TopicRequest
import kr.co.jojee.blog.api.entity.*
import kr.co.jojee.blog.api.repository.PostRepository
import kr.co.jojee.blog.api.repository.PostTagRepository
import kr.co.jojee.blog.api.repository.TagRepository
import kr.co.jojee.blog.api.repository.TopicRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import javax.transaction.Transactional

@Service
class BlogService(
    val postRepository: PostRepository,
    val topicRepository: TopicRepository,
    val tagRepository: TagRepository,
    val postTagRepository: PostTagRepository
) {
    @Transactional
    fun addPost(postRequest: PostRequest): Post {
        val post = Post(
            title = postRequest.title,
            summary = postRequest.summary,
            topic = findTopicById(postRequest.topicId),
            published = postRequest.published,
            body = postRequest.body
        )

        setTags(post, postRequest.tags)

        return postRepository.save(post)
    }

    @Transactional
    fun updatePost(id: Long, postRequest: PostRequest): Post {
        val post = findPostById(id)

        post.title = postRequest.title
        post.summary = postRequest.summary
        post.topic = findTopicById((postRequest.topicId))
        post.published = postRequest.published
        post.body = postRequest.body

        postTagRepository.deleteAllByPost(post)

        setTags(post, postRequest.tags)

        return postRepository.save(post)
    }

    fun addTopic(name: String): Topic {
        val topic = Topic(
            name = name
        )
        return topicRepository.save(topic)
    }

    fun updateTopic(id: Long, topicRequest: TopicRequest): Topic {
        val topic = findTopicById(id)

        topic.name = topicRequest.name

        return topicRepository.save(topic)
    }

    fun findAllTopics(): List<Topic> {
        return topicRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))
    }

    fun findTopicById(id: Long): Topic {
        return topicRepository.findById(id).orElseThrow {throw ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 email입니다.")}
    }

    fun findAllPosts(pageable: Pageable): Page<Post> {
        return postRepository.findAll(pageable)
    }

    fun findTagByName(name: String): Tag? {
        return tagRepository.findByName(name)
    }

    fun findAllTags(): List<Tag> {
        return tagRepository.findAll()
    }

    fun findPostById(id: Long): Post {
        return postRepository.findById(id).orElseThrow {throw ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 post입니다.")}
    }

    fun setTags(post: Post, tags: List<String>) {
        post.tags.clear()

        for (tagName in tags) {
            val tag = findTagByName(tagName) ?: Tag(name = tagName)
            val postTag = PostTag(post = post, tag = tag)

            tag.posts.add(postTag)
            post.tags.add(postTag)

            tagRepository.save(tag)
        }}

    fun getAllPostIds(): List<Long> {
        return postRepository.findAllId()
    }

    fun findPostsByTag(tag: String, pageable: Pageable): Page<Post> {
        return postRepository.findPostsByTag(tag, pageable)
    }

    fun getAllTagNames(): List<String> {
        return tagRepository.getAllTagNames()
    }
}
