package kr.co.jojee.blog.api.controller

import io.swagger.v3.oas.annotations.tags.Tag
import kr.co.jojee.blog.api.dto.*
import kr.co.jojee.blog.api.service.BlogService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
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
    fun getPublishedPosts(
        @RequestParam(defaultValue = "0", required = false) page: Int,
        @RequestParam(defaultValue = "10", required = false) size: Int,
        @RequestParam(required = false) tag: String?
    ): Page<PostListResponse>{
        val pageRequest = PageRequest.of(page, size, Sort.by("id").descending())

        if (tag != null)
            return blogService.findPublishedPostsByTag(tag, pageRequest).map { it.toListDto() }
        return blogService.findPublishedPosts(pageRequest).map { it.toListDto() }
    }

    @GetMapping("/posts/ids")
    fun getAllPostIds(): List<Long> {
        return blogService.getAllPostIds()
    }
    @GetMapping("/posts/{id}")
    fun getPostbyId(
        @PathVariable id: Long
    ): PostResponse {
        return blogService.findPostById(id).toDto()
    }

    @PutMapping("/posts/{id}/viewcount")
    fun increaseViewCount(
        @PathVariable id: Long
    ): Long {
        return blogService.increaseViewCount(id)
    }

    @GetMapping("/posts/{id}/viewcount")
    fun getViewCount(
        @PathVariable id: Long
    ): Long {
        return blogService.findViewCountById(id)
    }

    @GetMapping("/topics")
    fun getAllTopics(): List<TopicResponse> {
        return blogService.findAllTopics().map { it.toDto() }
    }

    @GetMapping("/tags")
    fun getAllTags(): List<TagResponse> {
        return blogService.findAllTags().map { it.toDto() }
    }

    @GetMapping("/tags/names")
    fun getAllTagNames(): List<String> {
        return blogService.getAllTagNames()
    }
}