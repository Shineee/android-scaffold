package com.scaffold.app.domain.model

data class Post(
    val id: Int,
    val userId: Int,
    val title: String,
    val body: String,
) {
    /** 演示用封面图：根据 id 生成稳定的图片地址（Coil 加载示例） */
    val coverUrl: String
        get() = "https://picsum.photos/seed/post$id/120/120"
}
