package com.example.nmedia.repository

import androidx.lifecycle.LiveData
import com.example.nmedia.Post

interface PostRepository {
    fun getAll(): List<Post>
    fun likeById(id: Long)
    fun dislikeById(id: Long)
    fun repostById(id: Long)
    fun removeById(id:Long)
    fun save(post: Post)
    fun video()
    fun getPostById(id: Long): Post
    fun getAllAsync(callback: GetAllCallback)

    fun likeByIdAsync(id: Long, callback: SaveCallBack)
    fun dislikeByIdAsync(id: Long, callback: SaveCallBack)
    fun removeByIdAsync(id: Long, callback: ByIdCallBack)
    fun saveAsync(post: Post, callback: SaveCallBack)

    interface GetAllCallback {
        fun onSuccess(posts: List<Post>) {}
        fun onError(e: Exception) {}
    }

    interface ByIdCallBack {
        fun onSuccess() {}
        fun onError(e: Exception) {}
    }

    interface SaveCallBack {
        fun onSuccess(post: Post) {}
        fun onError(e: Exception) {}
    }

}