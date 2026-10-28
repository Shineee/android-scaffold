package com.scaffold.app.data.local.post

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {
    @Query("SELECT * FROM posts ORDER BY id ASC")
    fun observeAll(): Flow<List<PostEntity>>

    @Upsert
    suspend fun upsertAll(posts: List<PostEntity>)

    @Query("DELETE FROM posts")
    suspend fun clear()
}
