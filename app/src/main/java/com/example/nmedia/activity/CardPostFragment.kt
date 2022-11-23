package com.example.nmedia.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.nmedia.R
import com.example.nmedia.databinding.FragmentCardPostBinding
import com.example.nmedia.Post
import com.example.nmedia.util.PostArg
import com.example.nmedia.util.StringArg
import com.example.nmedia.viewmodel.PostViewModel


class CardPostFragment : Fragment() {
    companion object {
        var Bundle.postArg: Post? by PostArg
        var Bundle.textArg: String? by StringArg
    }

    private val viewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentCardPostBinding.inflate(inflater, container, false)

        arguments?.postArg?.let {
            binding.apply {
                author.text = it.author
                published.text = it.published
                content.text = it.content
                like.isChecked = it.likedByMe
                like.text = "${it.likes}"
                if (!it.videoUrl.isNullOrEmpty()) {
                    video.visibility = View.VISIBLE
                } else video.visibility = View.GONE
                menu.setOnClickListener { it ->
                    PopupMenu(it.context, it).apply {
                        inflate(R.menu.options_post)
                        setOnMenuItemClickListener { item ->
                            when (item.itemId) {
                                R.id.remove -> {
                                    findNavController().navigateUp()
                                    true
                                }
                                R.id.edit -> {
                                    findNavController().navigate(R.id.editPostFragment,
                                        Bundle().apply
                                        {
                                            //textArg = it.content

                                        })
                                    true
                                }
                                else -> false
                            }
                        }
                    }.show()
                }

            }
        }

        return binding.root
    }
}