package com.example.nmedia



data class Post (
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    val likes: Int = 0,
    val likedByMe: Boolean = false,
    //var countReposts: Int = 0,
   // var countLikes: Int = 1
)