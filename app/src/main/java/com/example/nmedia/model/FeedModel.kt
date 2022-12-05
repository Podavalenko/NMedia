package com.example.nmedia.model

import com.example.nmedia.Post

data class FeedModel(
    val posts: List<Post> = emptyList(),
    val loading:Boolean = false,
    val error: Boolean = false,
    val empty: Boolean = false,
    val refresh: Boolean = false,
)
