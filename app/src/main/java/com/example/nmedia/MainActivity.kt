package com.example.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.activity.viewModels
import com.example.nmedia.databinding.ActivityMainBinding
import com.example.nmedia.viewmodel.PostViewModel
import java.text.DecimalFormat
import com.example.nmedia.adapter.PostsAdapter
import com.example.nmedia.databinding.PostCardBinding



class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

       // val viewModel by viewModels<PostViewModel>()
        val viewModel: PostViewModel by viewModels()
        val adapter = PostsAdapter({
            viewModel.likeById(it.id)
        },{
            viewModel.likeById(it.id)
        })
        binding.list.adapter = adapter
        viewModel.data.observe(this, { posts ->
            adapter.submitList(posts)


        })
    }
}

    //fun displayNumbers(number: Int): String {
    //    val decimalFormat = DecimalFormat("#.#")
     //   return when (number) {
     //       in 0..999 -> "$number"
     //       in 1000..99_999 -> "${decimalFormat.format(number.toFloat() / 1_000)}K"
     //       in 10_000..999_999 -> "${number / 1_000}K"
     //       else -> "${decimalFormat.format(number.toFloat() / 1_000_000)}M"





