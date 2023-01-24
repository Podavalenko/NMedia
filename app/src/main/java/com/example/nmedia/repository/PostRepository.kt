package com.example.nmedia.repository

import androidx.lifecycle.LiveData
import com.example.nmedia.dto.Post
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    val data: Flow<List<Post>>

    suspend fun getAll()
    fun getNewerCount(id: Long): Flow<Int>
    suspend fun updatePosts()
    suspend fun getPostById(id: Long)
    suspend fun likeById(id: Long)
    suspend fun dislikeById(id: Long)
    suspend fun removeById(id: Long)
    suspend fun save(post: Post)
    suspend fun repostById(id: Long)
    suspend fun video()
}