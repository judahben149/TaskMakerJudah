package com.judahben149.taskmakerjudah.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.judahben149.taskmakerjudah.R
import com.judahben149.taskmakerjudah.TaskRepository
import com.judahben149.taskmakerjudah.models.entities.Task
import com.judahben149.taskmakerjudah.ui.taskList.activity.TaskListActivity
import kotlinx.coroutines.delay

class DailyWorker(context: Context, workerParameters: WorkerParameters): CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {

        val tasks = TaskRepository.getInstance(applicationContext).getAllTasksAsList()
        delay(1000L)

        if (tasks.isEmpty()) {
            Result.retry()
        } else {
            createNotificationChannel(applicationContext)
            sendNotification(tasks)
        }

        return Result.success()
    }




    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)

            NotificationManagerCompat.from(context).createNotificationChannel(channel)
        }
    }



    private fun sendNotification(tasks: List<Task>) {
        val notificationStyle = NotificationCompat.InboxStyle()

        notificationStyle.setBigContentTitle("Pending Tasks")
            .setSummaryText("Daily reminder yo!")
            if (tasks.isNotEmpty()) {
                tasks.forEach {tasks ->
                    notificationStyle.addLine(tasks.taskTitle)
                }
            } else return

        val intent = Intent(applicationContext, TaskListActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(applicationContext, NOTIFICATION_REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID).apply {
            setSmallIcon(R.drawable.ic_add)
            setPriority(NotificationCompat.PRIORITY_HIGH)
            setContentIntent(pendingIntent)
            setStyle(notificationStyle)
        }.build()

        val notificationManager = NotificationManagerCompat.from(applicationContext)
        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    companion object {
        const val NOTIFICATION_ID = 112
        const val CHANNEL_ID = "1122"
        const val CHANNEL_NAME = "CHANNEL_TWO"
        const val NOTIFICATION_REQUEST_CODE = 11
        const val WORK_NAME = "Daily worker"

    }
}