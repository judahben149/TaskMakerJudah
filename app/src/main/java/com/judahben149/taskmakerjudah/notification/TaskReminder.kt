package com.judahben149.taskmakerjudah.notification

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import com.judahben149.taskmakerjudah.R
import com.judahben149.taskmakerjudah.models.entities.Task
import com.judahben149.taskmakerjudah.ui.detailTask.activity.TaskDetailActivity
import com.judahben149.taskmakerjudah.utils.DateConverter

class TaskReminder : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        Log.d("reminder", "onReceive just called")

        val bundle = intent?.extras
        val taskId = bundle?.getInt("TASK_ID")
        val taskTitle = bundle?.getString("TASK_TITLE")
        val taskDueDate = bundle?.getLong("TASK_DATE")

        createNotificationChannel(context)

        if (taskId != null && taskTitle!= null && taskDueDate!= null) {
            createTaskNotification(context, taskId, taskTitle, taskDueDate)
        }
    }

    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)

            NotificationManagerCompat.from(context).createNotificationChannel(channel)
            Log.d("reminder", "Notification channel just set")
        }
    }

    fun createTaskNotification(context: Context, taskId: Int, taskTitle: String, taskDueDate: Long) {

        val dueDate = DateConverter.convertTimeMillisToString(taskDueDate)
        val intent = Intent(context, TaskDetailActivity::class.java)
        intent.putExtra("TASK_ID", taskId)
        Log.d("reminder", taskId.toString())

        val pendingIntent = PendingIntent.getActivity(context, NOTIFICATION_REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT)


        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_sort_svgrepo_com)
            .setContentTitle(taskTitle)
            .setAutoCancel(true)
            .setContentText(context.getString(R.string.due_date_notification, dueDate))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .build()

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(NOTIFICATION_ID, notification)
        Log.d("reminder", "Notification sent")

    }

    fun setTaskReminder(context: Context, task: Task, reminderTime: Long) {
        val intent = Intent(context, TaskReminder::class.java)
        val bundle = Bundle()
        bundle.putInt("TASK_ID", task.id)
        bundle.putString("TASK_TITLE", task.taskTitle)
        bundle.putLong("TASK_DATE", task.dateDueMillis)

        intent.putExtras(bundle)

        val pendingIntent = PendingIntent.getBroadcast(context, TASK_REMINDER_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, reminderTime, pendingIntent)
        Log.d("reminder", "Alarm manager scheduled")
    }

    companion object {
        const val CHANNEL_ID = "10"
        const val CHANNEL_NAME = "TASK"
        const val NOTIFICATION_ID = 0
        const val NOTIFICATION_REQUEST_CODE = 10
        const val TASK_REMINDER_ID = 123
    }
}
