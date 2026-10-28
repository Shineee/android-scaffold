package com.scaffold.app.ui.home

import com.scaffold.app.util.FakePostRepository
import com.scaffold.app.util.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `initial load refreshes and exposes posts`() = runTest {
        val repository = FakePostRepository()
        val viewModel = HomeViewModel(repository)
        // posts 使用 WhileSubscribed，必须有订阅者才会开始收集
        val subscription = launch { viewModel.posts.collect { } }
        advanceUntilIdle()

        assertEquals(1, repository.refreshCount)
        assertEquals(1, viewModel.posts.value.size)
        assertNull(viewModel.error.value)
        assertFalse(viewModel.isRefreshing.value)

        subscription.cancel()
    }

    @Test
    fun `failed refresh with empty cache surfaces error`() = runTest {
        val repository = FakePostRepository().apply {
            refreshResult = Result.failure(RuntimeException("network down"))
        }
        val viewModel = HomeViewModel(repository)
        advanceUntilIdle()

        assertEquals("network down", viewModel.error.value)
        assertEquals(0, viewModel.posts.value.size)
    }

    @Test
    fun `clearError resets error state`() = runTest {
        val repository = FakePostRepository().apply {
            refreshResult = Result.failure(RuntimeException("boom"))
        }
        val viewModel = HomeViewModel(repository)
        advanceUntilIdle()

        viewModel.clearError()
        assertNull(viewModel.error.value)
    }
}
