// package com.example.nmedia.repository
//
// import android.content.Context
// import androidx.lifecycle.LiveData
// import androidx.lifecycle.MutableLiveData
// import com.google.gson.Gson
// import com.google.gson.reflect.TypeToken
// import com.example.nmedia.dao.PostDao
// import com.example.nmedia.Post
// import java.text.DecimalFormat
//
// abstract class PostRepositorySQLiteImpl(
// private val dao: PostDao,
// ) : PostRepository {
// private var posts = emptyList<Post>().reversed()
//
// private val data = MutableLiveData(posts)
//
// init {
// posts = dao.getAll()
// data.value = posts
// }
//
// // override fun getAll(): LiveData<List<Post>> = data
//
// override fun likeById(id: Long) {
// dao.likeById(id)
// posts = posts.map {
// if (it.id != id) it else it.copy(
// likedByMe = !it.likedByMe,
// likes = if (!it.likedByMe) it.likes + 1 else it.likes - 1
// )
// }
// data.value = posts
// }
//
// override fun dislikeById(id: Long) {
// TODO("Not yet implemented")
// }
//
// override fun repostById(id: Long) {
// posts = posts.map {
// if (it.id != id) it else it.copy(reposts = it.reposts + 1)
// }
// data.value = posts
// }
//
// override fun removeById(id: Long) {
// dao.removeById(id)
// posts = posts.filter { it.id != id }
// data.value = posts
// }
//
// override fun save(post: Post) {
// val id = post.id
// val saved = dao.save(post)
// posts = if (id == 0L) {
// listOf(saved) + posts
// } else {
// posts.map {
// if (it.id != id) it else saved
// }
// }
// data.value = posts
// }
// //
//
// override fun video() {
// data.value = posts
// }
//
// }