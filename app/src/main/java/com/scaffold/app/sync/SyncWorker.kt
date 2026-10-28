package com.scaffold.app.sync

import android.content.Context
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.scaffold.app.domain.repository.PostRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * 后台同步示例。默认不调度，按需调用 [schedule]：
 * SyncWorker.schedule(context)
 */
class SyncWorker(
    context: Context,
    params: WorkerParameters,
) : CoroutineWorker(context, params), KoinComponent {

    private val repository: PostRepository by inject()

    override suspend fun doWork(): Result = repository.refresh().fold(
        onSuccess = { Result.success() },
        onFailure = { if (runAttemptCount < 3) Result.retry() else Result.failure() },
    )

    companion object {
        private const val UNIQUE_WORK = "post_sync"

        fun schedule(context: Context) {
            val request = OneTimeWorkRequestBuilder<SyncWorker>()
                .setConstraints(
                    Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.CONNECTED)
                        .build()
                )
                .build()
            WorkManager.getInstance(context)
                .enqueueUniqueWork(UNIQUE_WORK, ExistingWorkPolicy.REPLACE, request)
        }
    }
}
