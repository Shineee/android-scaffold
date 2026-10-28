package com.scaffold.app.di

import androidx.room.Room
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.scaffold.app.BuildConfig
import com.scaffold.app.data.local.SettingsRepository
import com.scaffold.app.data.local.post.PostDao
import com.scaffold.app.data.local.AppDatabase
import com.scaffold.app.data.remote.ApiConfig
import com.scaffold.app.data.remote.JsonPlaceholderApi
import com.scaffold.app.data.repository.PostRepositoryImpl
import com.scaffold.app.domain.repository.PostRepository
import com.scaffold.app.ui.home.HomeViewModel
import com.scaffold.app.ui.settings.SettingsViewModel
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

val appModule = module {

    // ---------- 网络 ----------
    single {
        Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
            explicitNulls = false
        }
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = if (BuildConfig.DEBUG) {
                        HttpLoggingInterceptor.Level.BODY
                    } else {
                        HttpLoggingInterceptor.Level.NONE
                    }
                }
            )
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(ApiConfig.BASE_URL)
            .client(get())
            .addConverterFactory(get<Json>().asConverterFactory("application/json".toMediaType()))
            .build()
    }

    single<JsonPlaceholderApi> {
        get<Retrofit>().create(JsonPlaceholderApi::class.java)
    }

    // ---------- 本地存储 ----------
    single {
        Room.databaseBuilder(get(), AppDatabase::class.java, "scaffold.db")
            .build()
    }

    single<PostDao> { get<AppDatabase>().postDao() }

    single { SettingsRepository(get()) }

    // ---------- 仓库 ----------
    single<PostRepository> { PostRepositoryImpl(get(), get()) }

    // ---------- ViewModel ----------
    viewModel { HomeViewModel(get()) }
    viewModel { SettingsViewModel(get()) }
}
