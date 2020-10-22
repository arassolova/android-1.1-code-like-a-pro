package ru.netology.nmedia.dto

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    var likebyMe: Boolean = false,
    var likes: Int = 0,
    var share: Int = 0
)
