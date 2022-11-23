package com.example.nmedia.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.nmedia.R
import com.example.nmedia.activity.CardPostFragment.Companion.postArg
import com.example.nmedia.activity.NewPostFragment.Companion.textArg
import com.example.nmedia.adapter.OnInterfactionListener
//import com.example.nmedia.adapter.PostsAdapter
import com.example.nmedia.databinding.FragmentFeedBinding
import com.example.nmedia.Post
import com.example.nmedia.viewmodel.PostViewModel

class FragmentFeed : Fragment() {
    private val viewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentFeedBinding.inflate(inflater, container, false)

        val adapter = OnInterfactionListener.PostsAdapter(object : OnInterfactionListener {
            override fun onEdit(post: Post) {
                findNavController().navigate(R.id.action_fragmentFeed_to_editPostFragment,
                    Bundle().apply
                    {
                        textArg = post.content
                        viewModel.edit(post)
                    })
            }

            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
            }

            override fun onRepost(post: Post) {
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, post.content)
                    type = "text/plane"
                }
                val repostIntent = Intent.createChooser(intent, getString(R.string.chooser_repost))
                startActivity(repostIntent)
                viewModel.repostById(post.id)
            }

            override fun onVideo(post: Post) {
                post.videoUrl?.let { viewModel.video() }
                if (!post.videoUrl.isNullOrEmpty()) {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.videoUrl))
                    startActivity(intent)
                }
            }

            override fun onOpenPost(post: Post) {
                findNavController().navigate(R.id.action_fragmentFeed_to_cardPostFragment,
                    Bundle().apply
                    {
                        postArg = post
                    })
            }
        })

        binding.list.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner, { posts ->
            adapter.submitList(posts)
        })

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentFeed_to_newPostFragment)
        }

        return binding.root
    }
}




