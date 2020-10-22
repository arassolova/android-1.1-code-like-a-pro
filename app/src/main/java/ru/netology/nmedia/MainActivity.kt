package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post: Post = Post(
            id = 1,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            published = "21 мая в 18:36",
            likebyMe = false,
            likes = 1,
            share = 1
        )
        with(binding) {
            txtAuthor.text = post.author
            txtPublished.text = post.published
            txtContent.text = post.content
            txtLikes.text = numbersString(post.likes)
            txtShare.text = numbersString(post.share)

            if (post.likebyMe) {
                imgLike?.setImageResource(R.drawable.ic_liked_24)
                txtLikes.setText(numbersString(post.likes + 1))
            }

            imgLike?.setOnClickListener {
                post.likebyMe = !post.likebyMe
                imgLike.setImageResource(
                    if (post.likebyMe) R.drawable.ic_liked_24 else R.drawable.ic_favorite_24
                )
                txtLikes.setText(
                    if (post.likebyMe) numbersString(post.likes + 1)
                    else numbersString(post.likes)
                )
            }

            imgShare?.setOnClickListener {
                post.share++
                txtShare.setText(numbersString(post.share))
            }
        }
    }

    fun numbersString(number: Int): String {
        return when (number) {
            in 0..999 -> number.toString()
            in 1_000..9_999 -> if ((number / 100) % 10 == 0) {
                (number / 1_000).toString() + "K"
            } else {
                String.format("%.1f", number * 1.0 / 1_000) + "K"
            }
            in 10_000..999_999 -> (number / 1_000).toString() + "K"
            else -> if ((number / 100_000) % 10 == 0) {
                (number / 1000_000).toString() + "M"
            } else {
                String.format("%.1f", number * 1.0 / 1000_000) + "M"
            }

        }
    }
}