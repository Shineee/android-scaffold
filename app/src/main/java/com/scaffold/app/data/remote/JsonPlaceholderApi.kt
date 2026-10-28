package com.scaffold.app.data.remote

import retrofit2.http.GET

interface JsonPlaceholderApi {
    @GET("posts")
    suspend fun getPosts(): List<PostDto>
}
