package kr.co.jojee.blog.api.dto

data class PostListResponse(
    val id: Long?,
    val title: String,
    val summary: String,
    val tags: List<String>,
    val published: Boolean,
    val createdAt: String,
    val updatedAt: String,
    val publishedAt: String
)
