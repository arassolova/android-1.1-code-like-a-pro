package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.util.AndroidUtils
import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()
        val adapter = PostsAdapter(object:OnInteractionListener {
            override fun onLike(post: Post)
            {
                viewModel.likeById(post.id)
            }

            override fun onShare(post: Post) {
                viewModel.shareById(post.id)
            }

            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
            }

            override fun onEdit(post: Post) {
                viewModel.edit(post)
            }
        })
        binding.listView.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }

        viewModel.edited.observe(this){post->
            if (post.id == 0L) {
                return@observe
            }
            with(binding.contentEdit) {
                requestFocus()
                setText(post.content)
            }
            with(binding.grpEditMessage) {
                grpEditMessage.visibility = View.VISIBLE
                txtEditPost.setText(post.content)
            }
        }

        binding.imgSave.setOnClickListener {
            with(binding.contentEdit){
                if (TextUtils.isEmpty(text)) {
                    Toast.makeText(
                        this@MainActivity, "Content can not be empty", Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }

                viewModel.changeContent(text.toString())
                viewModel.save()

                setText("")
                clearFocus()
                AndroidUtils.hideKeyboard(this)
            }
            with(binding.grpEditMessage) {
                grpEditMessage.visibility = View.GONE
                txtEditPost.setText("")
            }
        }

        binding.btnCancelEdit.setOnClickListener {
            grpEditMessage.visibility = View.GONE
            with(binding.contentEdit){
                setText("")
                clearFocus()
                AndroidUtils.hideKeyboard(this)
            }
        }
    }
}
