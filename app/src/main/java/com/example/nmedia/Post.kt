package com.example.nmedia

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Post (
    val id: Long,
    val author: String,
    val authorAvatar: String?,
    val content: String,
    val published: String,
    val likedByMe: Boolean = false,
    val likes: Long = 0,
    val reposts: Long = 0,
    val videoUrl: String?

    ): Parcelable
