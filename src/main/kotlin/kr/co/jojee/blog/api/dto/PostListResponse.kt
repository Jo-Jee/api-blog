package kr.co.jojee.blog.api.dto

data class PostListResponse(
    val id: Long?,
    val title: String,
    val summary: String,
    val tags: List<String>,
    val publishedAt: String,
    val viewCount: Long
)
