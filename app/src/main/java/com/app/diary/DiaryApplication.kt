package com.app.diary

import android.app.Application
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.app.diary.repository.OnlineNoteRepository
import com.app.diary.util.ApiResultHandler
import com.app.diary.util.UserTokenManager
import com.app.diary.worker.SynchronizationWorker
import com.app.diary.worker.sync.SyncLocalToRemote
import com.app.diary.worker.sync.SyncRemoteToLocal
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class DiaryApplication : Application() {
    @Inject
    lateinit var userTokenManager: UserTokenManager
    @Inject
    lateinit var syncLocalToRemote: SyncLocalToRemote
    @Inject
    lateinit var syncRemoteToLocal: SyncRemoteToLocal

    override fun onCreate() {
        super.onCreate()
        if(userTokenManager.getToken()!=null){
            setupWorker()
        }
    }

    private fun setupWorker() {
        val constraint= Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        val workerRequestPeriodic=PeriodicWorkRequest.Builder(SynchronizationWorker::class.java,10,TimeUnit.MINUTES).build()
        WorkManager.getInstance(this).enqueue(workerRequestPeriodic)
    }
}