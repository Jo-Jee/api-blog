package kr.co.jojee.blog.api.controller

import io.swagger.v3.oas.annotations.security.SecurityRequirement
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
    name = "Admin",
    description = "블로그 어드민 API"
)
@RestController
@RequestMapping("/api/v1/admin")
class AdminController(
    val blogService: BlogService
) {
    @SecurityRequirement(name="bearerAuth")
    @PostMapping("/posts")
    fun addPost(@RequestBody postRequest: PostRequest): PostResponse {
        return blogService.addPost(postRequest).toDto()
    }

    @SecurityRequirement(name="bearerAuth")
    @PutMapping("/posts/{id}")
    fun updatePost(@PathVariable id: Long, @RequestBody postRequest: PostRequest): PostResponse {
        return blogService.updatePost(id, postRequest).toDto()
    }

    @SecurityRequirement(name="bearerAuth")
    @PostMapping("/topics")
    fun addTopic(@RequestBody newTopic: TopicRequest): TopicResponse {
        return blogService.addTopic(newTopic.name).toDto()
    }

    @SecurityRequirement(name="bearerAuth")
    @PutMapping("/topics/{id}")
    fun updateTopic(@PathVariable id: Long, @RequestBody topicRequest: TopicRequest): TopicResponse {
        return blogService.updateTopic(id, topicRequest).toDto()
    }

    @GetMapping("/posts")
    fun getAllPosts(
        @RequestParam(defaultValue = "0", required = false) page: Int,
        @RequestParam(defaultValue = "10", required = false) size: Int,
        @RequestParam(required = false) tag: String?
    ): Page<PostListResponse>{
        val pageRequest = PageRequest.of(page, size, Sort.by("id").descending())

        return blogService.findAllPosts(pageRequest).map { it.toListDto() }
    }
}