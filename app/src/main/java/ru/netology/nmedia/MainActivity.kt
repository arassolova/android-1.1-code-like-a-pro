package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()
        val adapter = PostsAdapter{
            viewModel.likeById(it.id)
        }
        binding.listView.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.list = posts
        }
    }
}
/*
val viewModel: PostViewModel by viewModels()
viewModel.data.observe(this) { post ->
    with(binding) {
        txtAuthor.text = post.author
        txtPublished.text = post.published
        txtContent.text = post.content
        txtLikes.text = numbersString(post.likes)
        txtShare.text = numbersString(post.share)

        imgLike.setImageResource(
            if (post.likedByMe) ru.netology.nmedia.R.drawable.ic_liked_24 else ru.netology.nmedia.R.drawable.ic_favorite_24
        )
    }
}
binding.imgLike.setOnClickListener {
    viewModel.like()
}
binding.imgShare.setOnClickListener {
    viewModel.share()
}*/