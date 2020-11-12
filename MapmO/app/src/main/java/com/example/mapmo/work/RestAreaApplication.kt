package com.example.mapmo.work

import android.app.Application
import android.os.Build
import androidx.work.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class RestAreaApplication: Application() {
    private val applicationScope = CoroutineScope(Dispatchers.Default)

    private fun delayedInit() {
        applicationScope.launch {
            setupRecurringWork()
        }
    }

    override fun onCreate() {
        super.onCreate()
        delayedInit()
    }

    private fun setupRecurringWork() {
        val constraints = Constraints.Builder()
                .apply {
                    if (Build.VERSION.SDK_INT >= 23){
                        setRequiresDeviceIdle(false)
                    }
                }
                .setRequiresBatteryNotLow(true)
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

        val repeatingRequest = PeriodicWorkRequestBuilder<RefreshDataWorker>(
                15, TimeUnit.MINUTES
        )
                .setConstraints(constraints)
                .build()

        WorkManager.getInstance(applicationContext)
                .enqueueUniquePeriodicWork(
                        RefreshDataWorker.WORK_NAME,
                        ExistingPeriodicWorkPolicy.REPLACE,
                        repeatingRequest
                )


    }


}
