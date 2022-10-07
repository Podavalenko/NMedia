package com.example.nmedia



data class Post (
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    val likedByMe: Boolean = false,
    val likes: Long = 0,
    val reposts: Long = 0,
    //var countReposts: Int = 0,
   // var countLikes: Int = 1
)