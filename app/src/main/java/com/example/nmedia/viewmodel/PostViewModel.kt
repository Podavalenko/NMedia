package com.example.nmedia.viewmodel

import androidx.lifecycle.ViewModel
import com.example.nmedia.repository.InMemoryPostRepository
import com.example.nmedia.repository.PostRepository
import androidx.lifecycle.MutableLiveData
import com.example.nmedia.Post

private val empty = Post(
    id = 0,
    author = "",
    content = "",
    published = "",
    likedByMe = false,
    likes = 0,
    reposts = 0,
    videoUrl = ""
)

class PostViewModel : ViewModel() {
    private val repository: PostRepository = InMemoryPostRepository()
    val data = repository.getAll()
    val edited = MutableLiveData(empty)
    fun likeById(id: Long) = repository.likeById(id)
    fun repostById(id: Long) = repository.repostById(id)
    fun removeById(id: Long) = repository.removeById(id)
    fun video() = repository.video()

    fun save() {
        edited.value?.let {
            repository.save(it)
        }
        edited.value = empty
    }

    fun edit(post: Post) {
        edited.value = post
    }

    fun changeContent(content: String) {
        val text = content.trim()
        if (edited.value?.content == text) {
            return
        }
        edited.value = edited.value?.copy(content = text)
    }
}