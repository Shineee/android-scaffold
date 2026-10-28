package com.scaffold.app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.scaffold.app.data.local.post.PostDao
import com.scaffold.app.data.local.post.PostEntity

@Database(
    entities = [PostEntity::class],
    version = 1,
    exportSchema = false,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao
}
