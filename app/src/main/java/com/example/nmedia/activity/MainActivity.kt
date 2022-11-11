package com.example.nmedia.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.example.nmedia.Post
import com.example.nmedia.R
import com.example.nmedia.databinding.ActivityMainBinding
import com.example.nmedia.viewmodel.PostViewModel
//import com.example.nmedia.adapter.PostsAdapter
import com.example.nmedia.adapter.OnInterfactionListener
import com.example.nmedia.util.AndroidUtils
import android.content.Intent
import android.net.Uri
import androidx.activity.result.launch


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()
        val adapter = OnInterfactionListener.PostsAdapter(object : OnInterfactionListener {
            override fun onEdit(post: Post) {

                viewModel.edit(post)
            }

            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
            }

            override fun onRepost(post: Post) {
                viewModel.repostById(post.id)
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, post.content)
                    type = "text/plane"
                }
                val repostIntent = Intent.createChooser(intent, getString(R.string.chooser_repost))
                startActivity(repostIntent)
            }

            override fun onVideo(post: Post) {
                post.videoUrl?.let { viewModel.video() }
                if (!post.videoUrl.isNullOrEmpty()) {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.videoUrl))
                    startActivity(intent)
                }
            }

        })

        binding.list.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }

        val newPostLauncher = registerForActivityResult(NewPostResultContract()) { result ->
            result ?: return@registerForActivityResult
            viewModel.changeContent(result)
            viewModel.save()
        }

        binding.fab.setOnClickListener {
            newPostLauncher.launch()
        }
        val editPostLauncher = registerForActivityResult(EditPostResultContract()) { result ->
            result ?: return@registerForActivityResult
            viewModel.changeContent(result)
            viewModel.save()
        }

        viewModel.edited.observe(this) {
            if (it.id == 0L) {
                return@observe
            }
            editPostLauncher.launch(it.content)
        }
    }
}






