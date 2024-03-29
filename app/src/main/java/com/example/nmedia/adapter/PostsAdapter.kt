package com.example.nmedia.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.nmedia.BuildConfig
import com.example.nmedia.R
import com.example.nmedia.databinding.PostCardBinding
import com.example.nmedia.dto.Post
import retrofit2.http.*
import java.text.DecimalFormat


interface OnInterfactionListener {
    fun onLike(post: Post) {}
    fun onEdit(post: Post) {}
    fun onRemove(post: Post) {}
    fun onRepost(post: Post) {}
    fun onVideo(post: Post) {}
    fun onOpenPost(post: Post) {}
}
class PostsAdapter(
    private val onInterfactionListener: OnInterfactionListener,
) : ListAdapter<Post, PostViewHolder>(PostDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = PostCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, onInterfactionListener)
    }
    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }
}
class PostViewHolder(
    private val binding: PostCardBinding,
    private val onInterfactionListener: OnInterfactionListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(post: Post) {
        binding.apply {
            author.text = post.author
            published.text = post.published
            avatar.loadCircleCrop("${BuildConfig.BASE_URL}/avatars/${post.authorAvatar}")
            content.text = post.content
            like.isChecked = post.likedByMe
            like.text = "${post.likes}"
            if (!post.videoUrl.isNullOrEmpty()) {
                video.visibility = View.VISIBLE
            } else video.visibility = View.GONE

            /*if (post.attachment != null) {
                attachmentView.visibility = View.VISIBLE
                val urlAttachment = "http://10.0.2.2:9999/images/${post.attachment.url}"
                Glide.with(attachmentView)
                    .load(urlAttachment)
                    .timeout(10_000)
                    .placeholder(R.drawable.ic_baseline_loading_24)
                    .error(R.drawable.ic_baseline_error_24)
                    .into(attachmentView)
            } else attachmentView.visibility = View.GONE*/
            menu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.options_post)
                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.remove -> {
                                onInterfactionListener.onRemove(post)
                                true
                            }
                            R.id.edit -> {
                                onInterfactionListener.onEdit(post)
                                true
                            }
                            else -> false
                        }
                    }
                }.show()
            }
            like.setOnClickListener {
                onInterfactionListener.onLike(post)
            }
            repost.setOnClickListener {
                onInterfactionListener.onRepost(post)
            }
            video.setOnClickListener {
                onInterfactionListener.onVideo(post)
            }
            content.setOnClickListener {
                onInterfactionListener.onOpenPost(post)
            }
            like.text = displayNumbers(post.likes)
            repost.text = displayNumbers(post.reposts)
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