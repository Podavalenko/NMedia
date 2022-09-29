package com.example.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.example.nmedia.databinding.ActivityMainBinding
import com.example.nmedia.viewmodel.PostViewModel
import java.text.DecimalFormat


class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel by viewModels<PostViewModel>()
        viewModel.data.observe(this) { post ->

            with(binding) {
                author.text = post.author
                published.text = post.published
                content.text = post.content
                val likeImage = if (post.likedByMe) {
                    R.drawable.ic_liked
                } else {
                    R.drawable.ic_like
                }
                like?.setImageResource(likeImage)
                countLike?.text = post.likes.toString()
                countRepost?.text = post.reposts.toString ()
            }
        }
            binding.like?.setOnClickListener {
                viewModel.like()
            }
                binding.repost.setOnClickListener {
                    viewModel.repost()
        }
   }

    fun displayNumbers(number: Int): String {
        val decimalFormat = DecimalFormat("#.#")
        return when (number) {
            in 0..999 -> "$number"
            in 1000..99_999 -> "${decimalFormat.format(number.toFloat() / 1_000)}K"
            in 10_000..999_999 -> "${number / 1_000}K"
            else -> "${decimalFormat.format(number.toFloat() / 1_000_000)}M"
        }
    }
}


