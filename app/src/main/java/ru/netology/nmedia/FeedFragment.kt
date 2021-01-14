package ru.netology.nmedia

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.FragmentFeedBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmodel.PostViewModel

class FeedFragment : Fragment() {
    private val viewModel: PostViewModel by viewModels(ownerProducer = ::requireParentFragment)
    private val binding by lazy {
        FragmentFeedBinding.inflate(layoutInflater)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val adapter = PostsAdapter(object : OnInteractionListener {
            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }
            override fun onShare(post: Post) {
                val intent = Intent().apply{
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, post.content)
                    type = "text/plain"
                }
                val shareIntent =
                    Intent.createChooser(intent, getString(R.string.chooser_share_post))
                startActivity(shareIntent)
            }
            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
            }
            override fun onEdit(post: Post) {
                viewModel.edit(post)
            }
        })
        binding.listView.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner) { posts ->
            adapter.submitList(posts)
        }
        viewModel.edited.observe(viewLifecycleOwner) { post ->
            if (post.id == 0L) {
                return@observe
            }
            findNavController().navigate(R.id.action_feedFragment_to_editPostFragment)
        }
        binding.btnAddPost.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_newPostFragment)
        }
        return binding.root
    }
}
