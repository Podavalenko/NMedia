package com.example.nmedia.viewmodel

import androidx.lifecycle.ViewModel
import com.example.nmedia.repository.InMemoryPostRepository
import com.example.nmedia.repository.PostRepository

class PostViewModel: ViewModel() {
    private val repository: PostRepository = InMemoryPostRepository ()
    val data = repository.getAll()
    fun likeById(id: Long) = repository.likeById(id)
    fun repostById(id: Long) = repository.repostById(id)
}