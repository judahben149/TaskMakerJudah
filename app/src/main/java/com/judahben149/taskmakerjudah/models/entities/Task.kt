package com.judahben149.taskmakerjudah.models.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "taskTitle")
    val taskTitle: String,

    @ColumnInfo(name = "isCompleted")
    val isCompleted: Boolean,

    @ColumnInfo(name = "isPriority")
    val isPriority: Boolean,

    @ColumnInfo(name = "dateDueMillis")
    val dateDueMillis: Long,

    @ColumnInfo(name = "timeDueMillis")
    val timeDueMillis: Long,

)
