package com.app.diary.worker

import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.app.diary.DiaryApplication
import com.app.diary.util.ApiResultHandler
import kotlinx.coroutines.*
import javax.inject.Inject

class SynchronizationWorker @Inject constructor(
    private val context: Context, workerParams: WorkerParameters
) : Worker(context, workerParams) {
    private val syncRemoteToLocal = (context as DiaryApplication).syncRemoteToLocal
    private val syncLocalToRemote = (context as DiaryApplication).syncLocalToRemote
    private val lifecycleOwner = (context as LifecycleOwner)
    override fun doWork(): Result {
        Log.d("#worker", "started")
        handleSynchronization()
        Log.d("#worker", "ended")
        return Result.success()
    }

    private fun handleSynchronization() {
        syncRemoteToLocal.getNotes()
        syncLocalToRemote.notesToCreate.observe(lifecycleOwner, Observer {
            if (it.isNotEmpty()) {
                CoroutineScope(Dispatchers.IO).launch {
                    syncLocalToRemote.sync()
                }
            }
        })
        syncLocalToRemote.notesToUpdate.observe(lifecycleOwner, Observer {
            if (it.isNotEmpty()) {
                CoroutineScope(Dispatchers.IO).launch {
                    syncLocalToRemote.sync()
                }
            }
        })
        syncLocalToRemote.notesToDelete.observe(lifecycleOwner, Observer {
            if (it.isNotEmpty()) {
                CoroutineScope(Dispatchers.IO).launch {
                    syncLocalToRemote.sync()
                }
            }
        })

        syncRemoteToLocal.notesList.observe(lifecycleOwner, Observer {
            when (it) {
                is ApiResultHandler.Failure -> {}
                is ApiResultHandler.Loading -> {}
                is ApiResultHandler.Success -> syncRemoteToLocal.sync()
            }
        })
        syncLocalToRemote.noteChangeStatus.observe(lifecycleOwner, Observer {
        })
    }
}