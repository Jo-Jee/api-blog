package kr.co.jojee.blog.api.controller

import io.swagger.v3.oas.annotations.tags.Tag
import kr.co.jojee.blog.api.dto.*
import kr.co.jojee.blog.api.service.BlogService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
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
        val pageRequest = PageRequest.of(page, size, Sort.by("id").descending())
        return blogService.findAllPosts(pageRequest).map { it.toDto() }
    }

    @PostMapping("/posts")
    fun addPost(@RequestBody postRequest: PostRequest): PostResponse {
        return blogService.addPost(postRequest).toDto()
    }

    @PutMapping("/posts/{id}")
    fun updatePost(@PathVariable id: Long, @RequestBody postRequest: PostRequest): PostResponse {
        return blogService.updatePost(id, postRequest).toDto()
    }

    @GetMapping("/topics")
    fun getAllTopics(): List<TopicResponse> {
        return blogService.findAllTopics().map { it.toDto() }
    }

    @PostMapping("/topics")
    fun addTopic(@RequestBody newTopic: TopicRequest): TopicResponse {
        return blogService.addTopic(newTopic.name).toDto()
    }

    @GetMapping("/tags")
    fun getAllTags(): List<TagResponse> {
        return blogService.findAllTags().map { it.toDto() }
    }
}