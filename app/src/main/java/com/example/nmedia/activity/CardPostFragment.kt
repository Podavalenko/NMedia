package com.example.nmedia.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.nmedia.databinding.FragmentCardPostBinding
import com.example.nmedia.viewmodel.PostViewModel
import com.example.nmedia.util.LongArg

class CardPostFragment : Fragment() {
    companion object {
        var Bundle.postId by LongArg
    }
    private val viewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentCardPostBinding.inflate(inflater, container, false)

        val postId = requireArguments().postId ?: error("Post id is required")
        viewModel.getPostById(postId).let {
        }
        return binding.root
    }
}
