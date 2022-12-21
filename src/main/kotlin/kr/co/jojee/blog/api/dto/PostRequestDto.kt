package kr.co.jojee.blog.api.dto

data class PostRequestDto(
    val id: Long?,
    val title: String,
    val summary: String,
    val topicId: Long,
    val published: Boolean,
    val tags: List<String>,
    val body: String
)
