package com.example.nmedia.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.nmedia.R
import com.example.nmedia.activity.NewPostFragment.Companion.textArg
import com.example.nmedia.adapter.OnInterfactionListener
import com.example.nmedia.adapter.PostsAdapter
import com.example.nmedia.databinding.FragmentFeedBinding
import com.example.nmedia.activity.CardPostFragment.Companion.postId
import com.example.nmedia.viewmodel.PostViewModel
import java.io.File
import com.google.android.material.snackbar.Snackbar
import com.example.nmedia.R.id.action_fragmentFeed_to_editPostFragment
import com.example.nmedia.dto.Post

class FragmentFeed : Fragment() {
    private val viewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFeedBinding.inflate(inflater, container, false)

        val adapter = PostsAdapter(object : OnInterfactionListener {
            override fun onEdit(post: Post) {
                findNavController().navigate(
                    action_fragmentFeed_to_editPostFragment,
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
                    type = "text/plain"
                }
                val repostIntent = Intent.createChooser(intent, getString(R.string.chooser_repost))
                startActivity(repostIntent)
                //viewModel.repostById(post.id)
            }

            override fun onVideo(post: Post) {
//                post.videoUrl?.let { viewModel.video() }
//                if (!post.videoUrl.isNullOrEmpty()) {
//                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.videoUrl))
//                    startActivity(intent)
//                }
            }

            override fun onOpenPost(post: Post) {
                findNavController().navigate(R.id.action_fragmentFeed_to_cardPostFragment,
                    Bundle().apply
                    {
                        postId = post.id
                    })
            }
        })

        binding.list.adapter = adapter
        viewModel.dataState.observe(viewLifecycleOwner) { state ->
            binding.progress.isVisible = state.loading
            binding.swipeRefreshLayout.isRefreshing = state.refresh
            if (state.error) {
                Snackbar.make(binding.root, R.string.error_loading, Snackbar.LENGTH_LONG)
                    .setAction(R.string.retry_loading) { viewModel.loadPosts() }
                    .show()
            }
        }

        viewModel.data.observe(viewLifecycleOwner) { state ->
            adapter.submitList(state.posts)
            binding.emptyText.isVisible = state.empty
        }

//        viewModel.networkError.observe(viewLifecycleOwner, {
//            Snackbar.make(requireView(), "${resources.getString(R.string.network_error)} $it", Snackbar.LENGTH_LONG).show()
//        })

//        binding.retryButton.setOnClickListener {
//            viewModel.loadPosts()
//        }

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentFeed_to_newPostFragment,
                Bundle().apply
                {
                    val file = File(context?.filesDir, "savecontent.json")
                    if (file.exists()) textArg = file.readText()
                })
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshPosts()
            //binding.swipeRefreshLayout.isRefreshing = false
        }

        return binding.root
    }
}




