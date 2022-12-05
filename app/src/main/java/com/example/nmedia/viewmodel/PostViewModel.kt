package com.example.nmedia.viewmodel

import androidx.lifecycle.ViewModel
import com.example.nmedia.repository.InMemoryPostRepository
import com.example.nmedia.repository.PostRepository
import androidx.lifecycle.MutableLiveData
import com.example.nmedia.Post
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.nmedia.repository.PostRepositoryImpl
import com.example.nmedia.db.AppDb
import com.example.nmedia.model.FeedModel
import com.example.nmedia.util.SingleLiveEvent
import java.io.IOException
import kotlin.concurrent.thread

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

class PostViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PostRepository = PostRepositoryImpl()
    private val _data = MutableLiveData(FeedModel())
    val data: LiveData<FeedModel>
        get() = _data
    val edited = MutableLiveData(empty)
    private val _postCreated = SingleLiveEvent<Unit>()
    val postCreated: LiveData<Unit>
        get() = _postCreated

    init {
        loadPosts()
    }

    fun loadPosts() {
        _data.value = FeedModel(loading = true)
        repository.getAllAsync(object : PostRepository.GetAllCallback {
            override fun onSuccess(posts: List<Post>) {
                _data.postValue(FeedModel(posts = posts, empty = posts.isEmpty()))
            }

            override fun onError(e: Exception) {
                _data.postValue(FeedModel(error = true))
            }
        })
    }

    fun likeById(id: Long) {
        val old = _data.value?.posts.orEmpty()
        if (_data.value?.posts.orEmpty().filter { it.id == id }.none { it.likedByMe }) {
            repository.likeByIdAsync(id, object : PostRepository.SaveCallBack {
                override fun onSuccess(post: Post) {
                    _data.postValue(FeedModel(posts = _data.value?.posts.orEmpty().map { if (it.id == post.id) post else it }))
                }
                override fun onError(e: Exception) {
                    _data.postValue(_data.value?.copy(posts = old))
                }
            })
        } else repository.dislikeByIdAsync(id, object : PostRepository.SaveCallBack {
            override fun onSuccess(post: Post) {
                _data.postValue(FeedModel(posts = _data.value?.posts.orEmpty().map { if (it.id == post.id) post else it }))
            }
            override fun onError(e: Exception) {
                _data.postValue(_data.value?.copy(posts = old))
            }
        })
    }

    fun removeById(id: Long) {
        val old = _data.value?.posts.orEmpty()
        repository.removeByIdAsync(id, object : PostRepository.ByIdCallBack {
            override fun onSuccess() {
                val posts = _data.value?.posts.orEmpty()
                    .filter { it.id != id }
                _data.postValue(
                    _data.value?.copy(posts = posts, empty = posts.isEmpty())
                )
            }
            override fun onError(e: Exception) {
                _data.postValue(_data.value?.copy(posts = old))
            }
        })
    }

    fun repostById(id: Long) = repository.repostById(id)
    fun video() = repository.video()
    fun getPostById(id: Long): Post = repository.getPostById(id)

    fun edit(post: Post) {
        edited.value = post
    }

    fun save() {
        edited.value?.let {
            repository.saveAsync(it, object : PostRepository.SaveCallBack {
                override fun onSuccess(post: Post) {
                    _postCreated.postValue(Unit)
                }
                override fun onError(e: Exception) {
                    edited.value
                }
            })
        }
        edited.value = empty
    }
    fun changeContent(content: String) {
        val text = content.trim()
        if (edited.value?.content == text) {
            return
        }
        edited.value = edited.value?.copy(content = text)
    }
}