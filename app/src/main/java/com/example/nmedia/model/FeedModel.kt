package com.example.nmedia.model

import com.example.nmedia.dto.Post


data class FeedModel(
    val posts: List<Post> = emptyList(),
    val empty: Boolean = false
)