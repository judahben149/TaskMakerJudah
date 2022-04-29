package com.judahben149.taskmakerjudah.utils

import java.text.SimpleDateFormat
import java.util.*

object TimeConverter{

    fun formatTime(timeMillis: Long): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timeMillis

        val simpleDateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        return simpleDateFormat.format(calendar.time)
    }
}