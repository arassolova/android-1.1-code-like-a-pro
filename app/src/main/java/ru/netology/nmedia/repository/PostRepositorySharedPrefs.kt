package ru.netology.nmedia.repository

import android.content.Context
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.card_post.view.*
import ru.netology.nmedia.dto.Post

class PostRepositorySharedPrefs (
    context: Context
): PostRepository {

    private companion object{
        const val FILE = "posts"
        const val POSTS_KEY = "POSTS_KEY"
    }
    private var nextId = 1L
    private val gson = Gson()
    private val preferences = context.getSharedPreferences(FILE, Context.MODE_PRIVATE)
    private val type = object: TypeToken<List<Post>>(){}.type
    private var posts = run{
        val serialized = preferences.getString(POSTS_KEY, null)
            ?: return@run emptyList<Post>()

        gson.fromJson(serialized, type)
    }

    private val data = MutableLiveData(posts)

    override fun getAll(): LiveData<List<Post>> = data

    override fun likeById(id: Long) {
        posts = posts.map {
            val likes = if (it.likedByMe) it.likes - 1 else it.likes + 1
            if (it.id != id) it else it.copy(likedByMe = !it.likedByMe, likes = likes)
        }
        data.value = posts
        sync()
    }

    override fun shareById(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(share = it.share + 1)
        }
        data.value = posts
        sync()
    }

    override fun removeById(id: Long) {
        posts = posts.filter { it.id != id }
        data.value = posts
        sync()
    }

    override fun save(post: Post) {
        if (post.id == 0L) {
            posts = listOf(
                post.copy(
                    id = nextId++,
                    author = "Me",
                    published = "now",
                    likedByMe = false
                )
            ) + posts
            data.value = posts
            return
        }

        posts = posts.map {
            if (it.id != post.id) it else it.copy(content = post.content)
        }
        data.value = posts
        sync()
    }

    private fun sync(){
        preferences.edit {
            putString(POSTS_KEY, gson.toJson(posts))
        }
    }
}