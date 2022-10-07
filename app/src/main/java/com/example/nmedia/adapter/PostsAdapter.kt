package com.example.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.nmedia.R
import com.example.nmedia.databinding.PostCardBinding
import com.example.nmedia.Post
import com.example.nmedia.repository.InMemoryPostRepository
import java.text.DecimalFormat

typealias OnLikeListener = (post: Post) -> Unit
typealias OnRepostListener = (post: Post) -> Unit

    class PostsAdapter(
        private val onLikeListener: OnLikeListener,
        private val onRepostListener: OnRepostListener
    ) : ListAdapter<Post, PostViewHolder>(PostDiffCallback()) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
            val binding = PostCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return PostViewHolder(binding, onLikeListener, onRepostListener)
        }

        override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
            val post = getItem(position)
            holder.bind(post)
        }
    }

    class PostViewHolder(
        private val binding: PostCardBinding,
        private val onLikeListener: OnLikeListener,
        private val onRepostListener: OnRepostListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Post) {
            binding.apply {
                author.text = post.author
                published.text = post.published
                content.text = post.content
                like.setImageResource(
                    if (post.likedByMe) R.drawable.ic_liked else R.drawable.ic_like
                )
                like.setOnClickListener {
                    onLikeListener(post)
                }
                repost.setOnClickListener {
                    onRepostListener(post)
                }
                countLike.text = displayNumbers(post.likes)
                countRepost.text = displayNumbers(post.reposts)
            }
        }
        private fun displayNumbers(number: Long): String {
            val decimalFormat = DecimalFormat("#.#")
            return when (number) {
                in 0..999 -> "$number"
                in 1000..99_999 -> "${decimalFormat.format(number.toFloat() / 1_000)}K"
                in 10_000..999_999 -> "${number / 1_000}K"
                else -> "${decimalFormat.format(number.toFloat() / 1_000_000)}M"
            }
        }
    }


    class PostDiffCallback : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem == newItem
        }
    }
