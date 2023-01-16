package com.example.nmedia.repository

import androidx.lifecycle.LiveData
import com.example.nmedia.Post

interface PostRepository {
    fun repostById(id: Long)
    fun video()

    fun getAll(callback: Callback<List<Post>>)
    fun getPostById(id: Long, callback: Callback<Post>)
    fun likeById(id: Long, callback: Callback<Post>)
    fun dislikeById(id: Long, callback: Callback<Post>)
    fun removeById(id: Long, callback: Callback<Unit>)
    fun save(post: Post, callback: Callback<Post>)

    interface Callback<T> {
        fun onSuccess(value: T) {}
        fun onError(e: Exception) {}
    }

}