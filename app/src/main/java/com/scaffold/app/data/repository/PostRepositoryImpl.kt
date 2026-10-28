package com.scaffold.app.data.repository

import com.scaffold.app.data.local.post.PostDao
import com.scaffold.app.data.local.post.PostEntity
import com.scaffold.app.data.remote.JsonPlaceholderApi
import com.scaffold.app.data.remote.PostDto
import com.scaffold.app.domain.model.Post
import com.scaffold.app.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * 标准离线优先（offline-first）模式：
 * UI 只观察 Room；refresh() 负责网络 → 落库。
 */
class PostRepositoryImpl(
    private val api: JsonPlaceholderApi,
    private val postDao: PostDao,
) : PostRepository {

    override val posts: Flow<List<Post>> = postDao.observeAll().map { entities ->
        entities.map { it.toDomain() }
    }

    override suspend fun refresh(): Result<Unit> = runCatching {
        val dtos = api.getPosts()
        postDao.upsertAll(dtos.map { it.toEntity() })
    }
}

private fun PostDto.toEntity() = PostEntity(
    id = id,
    userId = userId,
    title = title,
    body = body,
)

private fun PostEntity.toDomain() = Post(
    id = id,
    userId = userId,
    title = title,
    body = body,
)
