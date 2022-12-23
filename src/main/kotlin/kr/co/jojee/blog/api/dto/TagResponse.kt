package kr.co.jojee.blog.api.dto

data class TagResponse(
    val id: Long?,
    val name: String,
    val count: Int
) {
}