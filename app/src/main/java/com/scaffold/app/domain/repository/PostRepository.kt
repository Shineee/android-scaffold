package com.scaffold.app.domain.repository

import com.scaffold.app.domain.model.Post
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    /** 本地缓存（Room）实时流，UI 直接 collect */
    val posts: Flow<List<Post>>

    /** 拉取远端并写入缓存；失败返回 Result.failure，缓存保持不变 */
    suspend fun refresh(): Result<Unit>
}
