package ru.netology.nmedia

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.databinding.FragmentEditPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.util.AndroidUtils
import ru.netology.nmedia.viewmodel.PostViewModel

class EditPostFragment : Fragment() {

    private val viewModel: PostViewModel by viewModels(ownerProducer = ::requireParentFragment)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentEditPostBinding.inflate(layoutInflater, container, false)
        binding.textEditPost.setText(viewModel.edited.value?.content)

        binding.btnSaveEditPost.setOnClickListener{
            viewModel.changeContent(binding.textEditPost.text.toString())
            viewModel.save()
            AndroidUtils.hideKeyboard(it)
            findNavController().navigateUp()
        }
        binding.btnCancellEdit.setOnClickListener{
            viewModel.edited.value = Post(
                id = 0,
                author = "",
                content = "",
                published = "",
                likedByMe = false,
                likes = 0,
                share = 0
            )
            AndroidUtils.hideKeyboard(it)
            findNavController().navigateUp()
        }
        return binding.root
    }
}