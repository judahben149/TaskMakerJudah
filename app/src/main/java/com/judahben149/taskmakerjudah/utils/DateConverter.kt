package com.judahben149.taskmakerjudah.utils

import java.text.SimpleDateFormat
import java.util.*

object DateConverter {

    fun convertTimeMillisToString(timeMillis: Long): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timeMillis

        val sdf = SimpleDateFormat("dd MMM", Locale.getDefault())
        return sdf.format(calendar.time)
    }
}