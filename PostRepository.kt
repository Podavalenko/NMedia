package com.example.nmedia.repository

import androidx.lifecycle.LiveData
import com.example.nmedia.Post

interface PostRepository {
    fun get(): LiveData<Post>
    fun like ()
}