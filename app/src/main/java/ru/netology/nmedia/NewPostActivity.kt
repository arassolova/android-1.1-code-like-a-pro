package ru.netology.nmedia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import ru.netology.nmedia.databinding.ActivityNewPostBinding

class NewPostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityNewPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSaveNewPost.setOnClickListener{
            val result = Intent().putExtra(Intent.EXTRA_TEXT, binding.textNewPost.text.toString())
            setResult(RESULT_OK, result)
            finish()
        }

    }
}