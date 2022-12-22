package kr.co.jojee.blog.api.controller

import io.swagger.v3.oas.annotations.tags.Tag
import kr.co.jojee.blog.api.dto.TopicRequest
import kr.co.jojee.blog.api.dto.PostRequest
import kr.co.jojee.blog.api.dto.PostResponse
import kr.co.jojee.blog.api.dto.TopicResponse
import kr.co.jojee.blog.api.service.BlogService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(
    name = "Blog",
    description = "블로그 API"
)
@RestController
@RequestMapping("/api/v1/blog")
class BlogController(
    val blogService: BlogService
) {
    @GetMapping("/posts")
    fun getAllPosts(@RequestParam page: Int, @RequestParam size: Int): Page<PostResponse> {
        val pageRequest = PageRequest.of(page, size)
        return blogService.findAllPosts(pageRequest).map { it.toDto() }
    }

    @PostMapping("/posts")
    fun addPost(@RequestBody newPost: PostRequest): PostResponse {
        return blogService.addPost(newPost.title, newPost.summary, newPost.topicId, newPost.published, newPost.tags).toDto()
    }

    @PostMapping("/topics")
    fun addTopic(@RequestBody newTopic: TopicRequest): TopicResponse {
        return blogService.addTopic(newTopic.name).toDto()
    }
}