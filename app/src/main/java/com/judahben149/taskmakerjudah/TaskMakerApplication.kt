package com.judahben149.taskmakerjudah

import android.app.Application
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode
import androidx.preference.PreferenceManager
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.judahben149.taskmakerjudah.worker.DailyWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskMakerApplication(): Application() {

    val applicationScope = CoroutineScope(Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()

        val preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        preferences.getString(getString(R.string.key_app_theme), getString(R.string.auto)).apply {
            when(this) {
                "light" -> setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                "dark" -> setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                else -> setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            }

        }
        Log.d("workff", "Application oncreate reached")
        workManagerInit()
    }

    private fun workManagerInit() {
        applicationScope.launch {
            val constraints = Constraints.Builder().setRequiresBatteryNotLow(true).build()

            val oneTimeWorkRequest = OneTimeWorkRequestBuilder<DailyWorker>().setConstraints(constraints).build()

            val workManager = WorkManager.getInstance(applicationContext)

            workManager.enqueueUniqueWork(DailyWorker.WORK_NAME, ExistingWorkPolicy.KEEP, oneTimeWorkRequest)
            Log.d("workff", "Enqueue just finished")
        }

    }
}