package com.example.nmedia.dto

data class Post (
    val id: Long,
    val author: String,
    val authorAvatar: String?,
    val content: String,
    val published: String,
    var likedByMe: Boolean,
    val likes: Long,
    val reposts: Long,
    val videoUrl: String?,
    val wasRead: Boolean = false,
    //val attachment: Attachment?
)
