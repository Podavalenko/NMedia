package com.example.nmedia.dao

import com.example.nmedia.Post
import java.io.Closeable

interface PostDao {
    fun getAll(): List<Post>
    fun likeById(id: Long)
    fun repostById(id: Long)
    fun removeById(id: Long)
    fun save(post: Post): Post
    fun video()
}