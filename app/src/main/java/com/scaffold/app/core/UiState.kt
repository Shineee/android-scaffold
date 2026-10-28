package com.scaffold.app.core

/**
 * 界面状态统一建模：加载中 / 成功 / 失败。
 * ViewModel 对外暴露 [UiState]，Screen 只做 when 渲染。
 */
sealed interface UiState<out T> {
    data object Loading : UiState<Nothing>
    data class Success<T>(val data: T) : UiState<T>
    data class Error(val message: String) : UiState<Nothing>
}
