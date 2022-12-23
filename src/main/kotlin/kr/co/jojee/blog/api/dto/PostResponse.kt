package kr.co.jojee.blog.api.dto

data class PostResponse(
    val id: Long?,
    val title: String,
    val summary: String,
    val topicId: Long,
    val tags: List<String>,
    val published: Boolean,
    val body: String,
    val createdAt: String,
    val updatedAt: String
)
