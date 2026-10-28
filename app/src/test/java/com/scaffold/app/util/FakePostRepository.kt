package com.scaffold.app.util

import com.scaffold.app.domain.model.Post
import com.scaffold.app.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakePostRepository : PostRepository {

    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    override val posts: Flow<List<Post>> = _posts

    var refreshResult: Result<Unit> = Result.success(Unit)
    var refreshCount = 0
        private set

    fun setPosts(value: List<Post>) {
        _posts.value = value
    }

    override suspend fun refresh(): Result<Unit> {
        refreshCount++
        refreshResult.onSuccess {
            if (_posts.value.isEmpty()) {
                _posts.value = listOf(samplePost())
            }
        }
        return refreshResult
    }

    companion object {
        fun samplePost(id: Int = 1) = Post(
            id = id,
            userId = 1,
            title = "title-$id",
            body = "body-$id",
        )
    }
}
