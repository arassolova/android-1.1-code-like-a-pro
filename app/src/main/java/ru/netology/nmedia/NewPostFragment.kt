package ru.netology.nmedia

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.databinding.FragmentNewPostBinding
import ru.netology.nmedia.viewmodel.PostViewModel

class NewPostFragment : Fragment() {
    private val viewModel: PostViewModel by viewModels(ownerProducer = ::requireParentFragment)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentNewPostBinding.inflate(layoutInflater, container, false)

        binding.btnSaveNewPost.setOnClickListener{
            viewModel.changeContent(binding.textNewPost.text.toString())
            viewModel.save()
            findNavController().navigateUp()

        }

        return binding.root
    }
}