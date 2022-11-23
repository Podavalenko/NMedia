package com.example.nmedia.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import com.example.nmedia.R
import com.example.nmedia.activity.NewPostFragment.Companion.textArg

class AppActivity : AppCompatActivity(R.layout.activity_app) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent?.let {
            if (it.action != Intent.ACTION_SEND) {
                return@let
            }

            val text = it.getStringExtra(Intent.EXTRA_TEXT)
            if (text?.isNotBlank() == true) {
                intent.removeExtra(Intent.EXTRA_TEXT)
                findNavController(R.id.fragment_nav_host).navigate(
                    R.id.action_fragmentFeed_to_newPostFragment,
                    Bundle().apply {
                        textArg = text
                    })
            }
        }
    }
}